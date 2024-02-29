package io.axoniq.opportunity.coreapi.product;

import java.util.Objects;

public class ProductLineItem {

    // TODO check steven
    // TODO do we need both ProductId and "identifier"?
    private final ProductId productId;
    private final int identifier;
    // TODO Emmett - What's the difference between quantity and amount?
    private final long quantity;
    private final long amount;

    public ProductLineItem(ProductId productId, int identifier, long quantity, long amount) {
        this.productId = productId;
        this.identifier = identifier;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getIdentifier() {
        return identifier;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getAmount() {
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
        ProductLineItem that = (ProductLineItem) o;
        return identifier == that.identifier
                && quantity == that.quantity
                && amount == that.amount
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, identifier, quantity, amount);
    }

    @Override
    public String toString() {
        return "ProductLineItem{" +
                "productId=" + productId +
                ", identifier=" + identifier +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
