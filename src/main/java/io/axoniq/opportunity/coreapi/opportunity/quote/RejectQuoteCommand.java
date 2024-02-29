package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

// TODO Emmett - Does the RejectQuoteCommand need to life, or is the deadline for this sufficient?
public class RejectQuoteCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final QuoteId quoteId;

    public RejectQuoteCommand(OpportunityId opportunityId, QuoteId quoteId) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RejectQuoteCommand that = (RejectQuoteCommand) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId);
    }

    @Override
    public String toString() {
        return "RejectQuoteCommand{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                '}';
    }
}
