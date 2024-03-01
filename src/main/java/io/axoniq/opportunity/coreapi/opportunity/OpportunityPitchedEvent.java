package io.axoniq.opportunity.coreapi.opportunity;

import java.util.Objects;

public class OpportunityPitchedEvent implements OpportunityStageChangedEvent {

    private final OpportunityId opportunityId;

    public OpportunityPitchedEvent(OpportunityId opportunityId) {
        this.opportunityId = opportunityId;
    }

    @Override
    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    @Override
    public OpportunityStage getStage() {
        return OpportunityStage.PITCHED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpportunityPitchedEvent that = (OpportunityPitchedEvent) o;
        return Objects.equals(opportunityId, that.opportunityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId);
    }

    @Override
    public String toString() {
        return "OpportunityPitchedEvent{" +
                "opportunityId=" + opportunityId +
                ", stage==" + OpportunityStage.PITCHED +
                '}';
    }
}
