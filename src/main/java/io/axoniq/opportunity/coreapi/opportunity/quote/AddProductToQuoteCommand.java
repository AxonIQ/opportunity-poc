package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record AddProductToQuoteCommand(@TargetAggregateIdentifier OpportunityId opportunityId,
                                       QuoteId quoteId,
                                       ProductLineItem product) {

}
