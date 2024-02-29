package io.axoniq.opportunity.coreapi.product;

import org.axonframework.commandhandling.RoutingKey;

import java.util.Objects;

public class ReleaseReservationForProductCommand {

    @RoutingKey
    private final ProductId productKey;
    private final Integer amount;

    public ReleaseReservationForProductCommand(ProductId productKey, Integer amount) {
        this.productKey = productKey;
        this.amount = amount;
    }

    public ProductId getProductKey() {
        return productKey;
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
        return Objects.equals(productKey, that.productKey) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productKey, amount);
    }

    @Override
    public String toString() {
        return "ReleaseReservationForProductCommand{" +
                "productKey=" + productKey +
                ", amount=" + amount +
                '}';
    }
}
