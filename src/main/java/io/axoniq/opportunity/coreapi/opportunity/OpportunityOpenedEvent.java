package io.axoniq.opportunity.coreapi.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;

import java.time.Instant;

public record OpportunityOpenedEvent(OpportunityId opportunityId,
                                     AccountId accountId,
                                     String name,
                                     Instant endDate) implements OpportunityStageChangedEvent {

    @Override
    public OpportunityStage stage() {
        return OpportunityStage.RFP;
    }
}
