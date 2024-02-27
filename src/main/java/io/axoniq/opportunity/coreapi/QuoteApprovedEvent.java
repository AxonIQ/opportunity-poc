package io.axoniq.opportunity.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class QuoteApprovedEvent {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final QuoteId quoteId;

    public QuoteApprovedEvent(OpportunityId opportunityId, QuoteId quoteId) {
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
        QuoteApprovedEvent that = (QuoteApprovedEvent) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId);
    }

    @Override
    public String toString() {
        return "QuoteApprovedEvent{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                '}';
    }
}
