package io.axoniq.opportunity.coreapi.account;

import java.util.Objects;

public class AccountCreatedEvent {

    private final AccountId accountId;
    private final String name;

    public AccountCreatedEvent(AccountId accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountCreatedEvent that = (AccountCreatedEvent) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name);
    }

    @Override
    public String toString() {
        return "AccountCreatedEvent{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                '}';
    }
}
