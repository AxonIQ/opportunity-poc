package io.axoniq.opportunity.coreapi.opportunity;

public record OpportunityClosedWonEvent(OpportunityId opportunityId) implements OpportunityStageChangedEvent {

    @Override
    public OpportunityStage stage() {
        return OpportunityStage.CLOSED_WON;
    }
}
