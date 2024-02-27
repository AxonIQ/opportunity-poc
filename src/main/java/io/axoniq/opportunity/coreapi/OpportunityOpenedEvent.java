package io.axoniq.opportunity.coreapi;

import java.util.Objects;

public class OpportunityOpenedEvent {

    private final OpportunityId opportunityId;
    private final AccountId accountId;
    private final String name;

    public OpportunityOpenedEvent(OpportunityId opportunityId, AccountId accountId, String name) {
        this.opportunityId = opportunityId;
        this.accountId = accountId;
        this.name = name;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
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
        OpportunityOpenedEvent that = (OpportunityOpenedEvent) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(accountId, that.accountId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, accountId, name);
    }

    @Override
    public String toString() {
        return "OpportunityCreatedEvent{" +
                "opportunityId=" + opportunityId +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                '}';
    }
}
