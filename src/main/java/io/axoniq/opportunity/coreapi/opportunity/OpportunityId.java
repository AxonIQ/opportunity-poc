package io.axoniq.opportunity.coreapi.opportunity;

import java.util.Objects;
import java.util.UUID;

public class OpportunityId {

    private final UUID identifier;

    public OpportunityId() {
        this(UUID.randomUUID());
    }

    public OpportunityId(UUID identifier) {
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
        OpportunityId accountId = (OpportunityId) o;
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
