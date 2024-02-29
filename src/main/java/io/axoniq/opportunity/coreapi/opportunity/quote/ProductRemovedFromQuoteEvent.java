package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductId;

import java.util.Objects;

// TODO add logic to show the reason to the front-end as feedback
public class ProductRemovedFromQuoteEvent {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final ProductId productId;
    private final String reason;

    public ProductRemovedFromQuoteEvent(OpportunityId opportunityId,
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
        ProductRemovedFromQuoteEvent that = (ProductRemovedFromQuoteEvent) o;
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
        return "ProductRemovedFromQuoteEvent{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                ", productId=" + productId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
