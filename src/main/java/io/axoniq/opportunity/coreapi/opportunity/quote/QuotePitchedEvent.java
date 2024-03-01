package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;

import java.time.Instant;
import java.util.List;

public record QuotePitchedEvent(OpportunityId opportunityId,
                                QuoteId quoteId,
                                String name,
                                Instant validUntil,
                                List<ProductLineItem> products) {

}