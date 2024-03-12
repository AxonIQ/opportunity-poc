package io.axoniq.opportunity.coreapi.product;

import java.util.Objects;
import java.util.UUID;

public class ProductId {

    private final UUID identifier;

    public ProductId() {
        this(UUID.randomUUID());
    }

    public ProductId(String identifier) {
        this(UUID.fromString(identifier));
    }

    public ProductId(UUID identifier) {
        this.identifier = identifier;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductId accountId = (ProductId) o;
        return Objects.equals(identifier, accountId.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return identifier.toString();
    }
}
