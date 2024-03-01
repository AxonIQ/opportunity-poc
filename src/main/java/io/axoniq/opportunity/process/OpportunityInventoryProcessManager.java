package io.axoniq.opportunity.process;


import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteId;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuotePitchedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteRejectedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.RemoveProductFromQuoteCommand;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import io.axoniq.opportunity.coreapi.product.ReleaseReservationForProductCommand;
import io.axoniq.opportunity.coreapi.product.ReserveProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Saga
class OpportunityInventoryProcessManager {

    private OpportunityId opportunityId;
    private final Map<QuoteId, Map<ProductId, Integer>> reservedProductsPerQuote = new ConcurrentHashMap<>();

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(OpportunityOpenedEvent event) {
        opportunityId = event.opportunityId();
    }

    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(QuotePitchedEvent event) {
        QuoteId quoteId = event.quoteId();
        event.products()
             .forEach(product -> saveAndReserveProduct(quoteId, product));
    }

    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(ProductAddedToQuoteEvent event) {
        saveAndReserveProduct(event.quoteId(), event.product());
    }

    private void saveAndReserveProduct(QuoteId quoteId, ProductLineItem product) {
        ProductId productId = product.productId();
        reservedProductsPerQuote.compute(quoteId, (qId, products) -> {
            Map<ProductId, Integer> reservedProducts = products == null ? new HashMap<>() : products;
            reservedProducts.compute(productId, (id, count) -> (count == null ? 0 : count) + product.amount());
            return reservedProducts;
        });

        commandGateway.send(new ReserveProductCommand(productId, product.amount()))
                      .exceptionally(e -> {
                          commandGateway.send(new RemoveProductFromQuoteCommand(
                                  opportunityId, quoteId, productId, e.getMessage()
                          ));
                          return null;
                      });
    }

    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(QuoteRejectedEvent event) {
        releaseReservedProducts(reservedProductsPerQuote.get(event.quoteId()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(OpportunityClosedLostEvent event) {
        for (Map.Entry<QuoteId, Map<ProductId, Integer>> reservedProducts : reservedProductsPerQuote.entrySet()) {
            releaseReservedProducts(reservedProducts.getValue());
        }
    }

    private void releaseReservedProducts(Map<ProductId, Integer> reservedProducts) {
        for (Map.Entry<ProductId, Integer> countPerProduct : reservedProducts.entrySet()) {
            ProductId productId = countPerProduct.getKey();
            Integer amount = countPerProduct.getValue();
            commandGateway.send(new ReleaseReservationForProductCommand(productId, amount));
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(OpportunityClosedWonEvent event) {
        // Do nothing - @EndSaga already cleans this process
    }
}
