package io.axoniq.opportunity.coreapi.product;

public record ProductLineItem(ProductId productId, int catalogId, int quantity, int amount) {

}
