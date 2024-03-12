package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RejectQuoteCommand(@TargetAggregateIdentifier OpportunityId opportunityId, QuoteId quoteId) {

}
