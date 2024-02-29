package io.axoniq.opportunity.coreapi;

import java.util.Objects;

public class QuoteRejectedEvent {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;

    public QuoteRejectedEvent(OpportunityId opportunityId, QuoteId quoteId) {
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
        QuoteRejectedEvent that = (QuoteRejectedEvent) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId);
    }

    @Override
    public String toString() {
        return "QuoteRejectedEvent{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                '}';
    }
}
