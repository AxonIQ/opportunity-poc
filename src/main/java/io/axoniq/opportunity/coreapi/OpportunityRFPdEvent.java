package io.axoniq.opportunity.coreapi;

import java.util.Objects;

// TODO what does RFP stand for?
public class OpportunityRFPdEvent {

    private final OpportunityId opportunityId;

    public OpportunityRFPdEvent(OpportunityId opportunityId) {
        this.opportunityId = opportunityId;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpportunityRFPdEvent that = (OpportunityRFPdEvent) o;
        return Objects.equals(opportunityId, that.opportunityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId);
    }

    @Override
    public String toString() {
        return "OpportunityRFPdEvent{" +
                "opportunityId=" + opportunityId +
                '}';
    }
}
