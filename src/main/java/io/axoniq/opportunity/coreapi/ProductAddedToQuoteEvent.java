package io.axoniq.opportunity.coreapi;

import java.util.Objects;

public class ProductAddedToQuoteEvent {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final ProductId productId;
    private final int amount;

    public ProductAddedToQuoteEvent(OpportunityId opportunityId, QuoteId quoteId, ProductId productId, int amount) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
        this.productId = productId;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductAddedToQuoteEvent that = (ProductAddedToQuoteEvent) o;
        return amount == that.amount
                && Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, productId, amount);
    }

    @Override
    public String toString() {
        return "ProductAddedToQuoteEvent{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
