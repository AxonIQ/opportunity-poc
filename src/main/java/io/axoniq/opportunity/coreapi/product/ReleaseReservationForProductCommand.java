package io.axoniq.opportunity.coreapi.product;

import org.axonframework.commandhandling.RoutingKey;

import java.util.Objects;

public class ReleaseReservationForProductCommand {

    @RoutingKey
    private final ProductId productId;
    private final Integer amount;

    public ReleaseReservationForProductCommand(ProductId productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Integer getAmount() {
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
        ReleaseReservationForProductCommand that = (ReleaseReservationForProductCommand) o;
        return Objects.equals(productId, that.productId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount);
    }

    @Override
    public String toString() {
        return "ReleaseReservationForProductCommand{" +
                "productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
