package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.QuoteId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

class Quote {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final String name;

    private boolean approved;

    Quote(OpportunityId opportunityId, QuoteId quoteId, String name) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
        this.name = name;
    }

    // TODO this command needs to be pre-validated before sending, as it has an inventory requirement
    @CommandHandler
    public void handle(AddProductToQuoteCommand command) {
        apply(new ProductAddedToQuoteEvent(opportunityId, quoteId, command.getProductId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(QuoteApprovedEvent event) {
        this.approved = true;
    }

    public String getName() {
        return name;
    }

    // TODO add equals/hashcode
}
