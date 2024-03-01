package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;

public record QuoteApprovedEvent(OpportunityId opportunityId, QuoteId quoteId, int value) {

}
