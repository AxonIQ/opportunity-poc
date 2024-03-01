package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RemoveProductFromQuoteCommand(@TargetAggregateIdentifier OpportunityId opportunityId,
                                            QuoteId quoteId,
                                            ProductId productId,
                                            String reason) {

}
