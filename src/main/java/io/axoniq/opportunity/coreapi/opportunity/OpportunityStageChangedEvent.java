package io.axoniq.opportunity.coreapi.opportunity;

public interface OpportunityStageChangedEvent {

    OpportunityId opportunityId();

    OpportunityStage stage();
}
