package io.axoniq.opportunity.coreapi.account;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private final AccountId accountId;
    private final String name;

    public CreateAccountCommand(AccountId accountId, String name) {
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
        CreateAccountCommand that = (CreateAccountCommand) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name);
    }

    @Override
    public String toString() {
        return "CreateAccountCommand{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                '}';
    }
}
