package io.axoniq.opportunity.coreapi;

public interface OpportunityStageChangedEvent {

    OpportunityId getOpportunityId();

    OpportunityStage getStage();
}
