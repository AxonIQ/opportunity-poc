package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;

public record QuoteRejectedEvent(OpportunityId opportunityId, QuoteId quoteId) {

}
