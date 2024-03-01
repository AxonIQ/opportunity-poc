package io.axoniq.opportunity.coreapi.opportunity;

public record OpportunityClosedLostEvent(OpportunityId opportunityId) implements OpportunityStageChangedEvent {

    @Override
    public OpportunityStage stage() {
        return OpportunityStage.CLOSED_LOST;
    }
}
