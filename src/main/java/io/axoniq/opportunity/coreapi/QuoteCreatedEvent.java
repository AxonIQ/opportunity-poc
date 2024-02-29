package io.axoniq.opportunity.coreapi;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class QuoteCreatedEvent {

    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final String name;
    private final Instant validUntil;
    private final List<ProductLineItem> products;

    public QuoteCreatedEvent(OpportunityId opportunityId,
                             String name,
                             Instant validUntil,
                             List<ProductLineItem> products) {
        this(opportunityId, new QuoteId(UUID.randomUUID()), name, validUntil, products);
    }

    public QuoteCreatedEvent(OpportunityId opportunityId,
                             QuoteId quoteId,
                             String name,
                             Instant validUntil,
                             List<ProductLineItem> products) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
        this.name = name;
        this.validUntil = validUntil;
        this.products = products;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    public String getName() {
        return name;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public List<ProductLineItem> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuoteCreatedEvent that = (QuoteCreatedEvent) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId)
                && Objects.equals(name, that.name)
                && Objects.equals(validUntil, that.validUntil)
                && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, name, validUntil, products);
    }

    @Override
    public String toString() {
        return "QuoteCreatedEvent{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                ", name='" + name + '\'' +
                ", validUntil=" + validUntil +
                ", products=" + products +
                '}';
    }
}