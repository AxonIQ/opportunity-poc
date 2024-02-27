package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.QuoteId;
import org.axonframework.commandhandling.CommandHandler;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

class Quote {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;

    Quote(OpportunityId opportunityId, QuoteId quoteId) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
    }

    // TODO this command needs to be pre-validated before sending, as it has an inventory requirement
    @CommandHandler
    public void handle(AddProductToQuoteCommand command) {
        apply(new ProductAddedToQuoteEvent(opportunityId, quoteId, command.getProductId(), command.getAmount()));
    }
}
