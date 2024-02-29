package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.QuoteId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

class Quote {

    private final OpportunityId opportunityId;
    @EntityId
    private final QuoteId quoteId;
    private final String name;

    // TODO Emmett - How would you envision the "draft" state to be incorporated here? Is it needed for validation?
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

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    public String getName() {
        return name;
    }

    public boolean isApproved() {
        return approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        return approved == quote.approved
                && Objects.equals(opportunityId, quote.opportunityId)
                && Objects.equals(quoteId, quote.quoteId)
                && Objects.equals(name, quote.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, name, approved);
    }
}
