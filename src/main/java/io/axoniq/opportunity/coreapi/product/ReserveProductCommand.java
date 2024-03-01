package io.axoniq.opportunity.coreapi.product;

import org.axonframework.commandhandling.RoutingKey;

import java.util.Objects;

public class ReserveProductCommand {

    @RoutingKey
    private final ProductId productId;
    private final int amount;

    public ReserveProductCommand(ProductId productId, int amount) {
        this.productId = productId;
        this.amount = amount;
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
        ReserveProductCommand that = (ReserveProductCommand) o;
        return amount == that.amount && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount);
    }

    @Override
    public String toString() {
        return "ReserveProductCommand{" +
                "productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
