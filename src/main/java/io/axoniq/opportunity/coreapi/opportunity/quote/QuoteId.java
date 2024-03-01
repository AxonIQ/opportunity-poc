package io.axoniq.opportunity.coreapi.opportunity.quote;

import java.util.Objects;
import java.util.UUID;

public class QuoteId {

    private final UUID identifier;

    public QuoteId() {
        this(UUID.randomUUID());
    }

    public QuoteId(UUID identifier) {
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
        QuoteId accountId = (QuoteId) o;
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
