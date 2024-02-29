package io.axoniq.opportunity.coreapi.opportunity;

public interface OpportunityStageChangedEvent {

    OpportunityId getOpportunityId();

    OpportunityStage getStage();
}
