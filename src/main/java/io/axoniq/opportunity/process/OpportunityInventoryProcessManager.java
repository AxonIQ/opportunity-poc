package io.axoniq.opportunity.process;

import io.axoniq.opportunity.coreapi.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.ProductId;
import io.axoniq.opportunity.coreapi.QuoteId;
import io.axoniq.opportunity.coreapi.ReleaseReservationForProductCommand;
import io.axoniq.opportunity.coreapi.RemoveProductFromQuoteCommand;
import io.axoniq.opportunity.coreapi.ReserveProductCommand;
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
        opportunityId = event.getOpportunityId();
    }

    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(ProductAddedToQuoteEvent event) {
        ProductId productId = event.getProductId();
        reservedProductsPerQuote.compute(event.getQuoteId(), (quoteId, products) -> {
            Map<ProductId, Integer> reservedProducts = products == null ? new HashMap<>() : products;
            reservedProducts.compute(productId, (id, count) -> (count == null ? 0 : count) + event.getAmount());
            return reservedProducts;
        });

        commandGateway.send(new ReserveProductCommand(productId, event.getAmount()))
                      .exceptionally(e -> {
                          commandGateway.send(new RemoveProductFromQuoteCommand(
                                  opportunityId, event.getQuoteId(), productId, e.getMessage()
                          ));
                          return null;
                      });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(OpportunityClosedWonEvent event) {
        // Do nothing - @EndSaga already cleans this process
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "opportunityId")
    public void on(OpportunityClosedLostEvent event) {
        for (Map.Entry<QuoteId, Map<ProductId, Integer>> reservedProducts : reservedProductsPerQuote.entrySet()) {
            for (Map.Entry<ProductId, Integer> countPerProduct : reservedProducts.getValue().entrySet()) {
                ProductId productId = countPerProduct.getKey();
                Integer amount = countPerProduct.getValue();
                commandGateway.send(new ReleaseReservationForProductCommand(productId, amount));
            }
        }
    }
}
