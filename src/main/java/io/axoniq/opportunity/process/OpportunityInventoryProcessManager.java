package io.axoniq.opportunity.process;

import io.axoniq.opportunity.coreapi.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.ProductId;
import io.axoniq.opportunity.coreapi.RemoveProductFromQuoteCommand;
import io.axoniq.opportunity.coreapi.ReserveProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
class OpportunityInventoryProcessManager {

    private OpportunityId opportunityId;

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
}
