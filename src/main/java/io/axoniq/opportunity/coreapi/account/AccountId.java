package io.axoniq.opportunity.coreapi.account;

import java.util.Objects;
import java.util.UUID;

public class AccountId {

    private final UUID identifier;

    public AccountId(UUID identifier) {
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
        AccountId accountId = (AccountId) o;
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
