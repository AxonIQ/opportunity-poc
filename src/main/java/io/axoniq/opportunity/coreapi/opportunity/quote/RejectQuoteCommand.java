package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

// TODO Use command for testing in the UI
public record RejectQuoteCommand(@TargetAggregateIdentifier OpportunityId opportunityId, QuoteId quoteId) {

}
