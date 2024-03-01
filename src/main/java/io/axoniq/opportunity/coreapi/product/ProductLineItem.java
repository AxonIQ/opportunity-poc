package io.axoniq.opportunity.coreapi.product;

public record ProductLineItem(ProductId productId, int catalogId, int quantity, int amount) {

    public int value() {
        return quantity * amount;
    }
}
