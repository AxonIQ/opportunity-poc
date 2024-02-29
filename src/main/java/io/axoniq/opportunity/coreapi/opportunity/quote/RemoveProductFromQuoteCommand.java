package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class RemoveProductFromQuoteCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final ProductId productId;
    private final String reason;

    public RemoveProductFromQuoteCommand(OpportunityId opportunityId,
                                         QuoteId quoteId,
                                         ProductId productId,
                                         String reason) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
        this.productId = productId;
        this.reason = reason;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoveProductFromQuoteCommand that = (RemoveProductFromQuoteCommand) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId)
                && Objects.equals(productId, that.productId)
                && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, productId, reason);
    }

    @Override
    public String toString() {
        return "RemoveProductFromQuoteCommand{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                ", productId=" + productId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
