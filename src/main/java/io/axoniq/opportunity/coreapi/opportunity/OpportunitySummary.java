package io.axoniq.opportunity.coreapi.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;

import java.time.Instant;

public record OpportunitySummary(OpportunityId opportunityId,
                                 AccountId accountId,
                                 OpportunityStage stage,
                                 String name,
                                 int value,
                                 Instant endDate) {

    public double weightedValue() {
        return stage.winProbability() * value;
    }
}
