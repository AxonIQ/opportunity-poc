package io.axoniq.opportunity.coreapi.opportunity;

public record OpportunityPitchedEvent(OpportunityId opportunityId) implements OpportunityStageChangedEvent {

    @Override
    public OpportunityStage stage() {
        return OpportunityStage.PITCHED;
    }
}
