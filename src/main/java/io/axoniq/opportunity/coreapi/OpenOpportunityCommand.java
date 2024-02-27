package io.axoniq.opportunity.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.Objects;

public class OpenOpportunityCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final AccountId accountId;
    private final String name;
    // TODO Emmett - Is it correct to assume the endDate is attached when the opportunity is opened?
    private final Instant endDate;

    public OpenOpportunityCommand(OpportunityId opportunityId, AccountId accountId, String name, Instant endDate) {
        this.opportunityId = opportunityId;
        this.accountId = accountId;
        this.name = name;
        this.endDate = endDate;
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

    public Instant getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenOpportunityCommand that = (OpenOpportunityCommand) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(accountId, that.accountId)
                && Objects.equals(name, that.name)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, accountId, name, endDate);
    }

    @Override
    public String toString() {
        return "OpenOpportunityCommand{" +
                "opportunityId=" + opportunityId +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}
