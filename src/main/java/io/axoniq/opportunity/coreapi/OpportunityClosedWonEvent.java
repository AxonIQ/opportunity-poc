package io.axoniq.opportunity.coreapi;

import java.util.Objects;

public class OpportunityClosedWonEvent {

    private final OpportunityId opportunityId;

    public OpportunityClosedWonEvent(OpportunityId opportunityId) {
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
        OpportunityClosedWonEvent that = (OpportunityClosedWonEvent) o;
        return Objects.equals(opportunityId, that.opportunityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId);
    }

    @Override
    public String toString() {
        return "OpportunityClosedWonEvent{" +
                "opportunityId=" + opportunityId +
                '}';
    }
}
