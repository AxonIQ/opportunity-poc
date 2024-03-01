package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;

public record ProductAddedToQuoteEvent(OpportunityId opportunityId, QuoteId quoteId, ProductLineItem product) {

}
