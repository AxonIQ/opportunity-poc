package io.axoniq.opportunity.coreapi.account;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record OpenOpportunityCommand(@TargetAggregateIdentifier AccountId accountId,
                                     OpportunityId opportunityId,
                                     String name,
                                     int value,
                                     Instant endDate) {

}
