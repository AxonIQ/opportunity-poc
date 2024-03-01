package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.List;

public record PitchQuoteCommand(@TargetAggregateIdentifier OpportunityId opportunityId,
                                String name,
                                Instant validUntil,
                                List<ProductLineItem> products) {

}
