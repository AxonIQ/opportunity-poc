package io.axoniq.opportunity.coreapi;

import java.util.Objects;

public class OpportunityClosedWonEvent implements OpportunityStageChangedEvent {

    private final OpportunityId opportunityId;

    public OpportunityClosedWonEvent(OpportunityId opportunityId) {
        this.opportunityId = opportunityId;
    }

    @Override
    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    @Override
    public OpportunityStage getStage() {
        return OpportunityStage.CLOSED_WON;
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
                ", stage=" + OpportunityStage.CLOSED_WON +
                '}';
    }
}
