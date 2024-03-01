package io.axoniq.opportunity.coreapi.product;

import java.util.Objects;

public class ProductLineItem {

    private final ProductId productId;
    private final int catalogId;
    private final int quantity;
    private final int amount;

    public ProductLineItem(ProductId productId, int identifier, int quantity, int amount) {
        this.productId = productId;
        this.catalogId = identifier;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public int getQuantity() {
        return quantity;
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
        ProductLineItem that = (ProductLineItem) o;
        return catalogId == that.catalogId
                && quantity == that.quantity
                && amount == that.amount
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, catalogId, quantity, amount);
    }

    @Override
    public String toString() {
        return "ProductLineItem{" +
                "productId=" + productId +
                ", catalogId=" + catalogId +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
