package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductId;

// TODO add logic to show the reason to the front-end as feedback
public record ProductRemovedFromQuoteEvent(OpportunityId opportunityId,
                                           QuoteId quoteId,
                                           ProductId productId,
                                           String reason) {

}
