package io.axoniq.opportunity.coreapi.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record OpenOpportunityCommand(@TargetAggregateIdentifier OpportunityId opportunityId,
                                     AccountId accountId,
                                     String name,
                                     Instant endDate) {

}
