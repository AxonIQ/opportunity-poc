package io.axoniq.opportunity.coreapi.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;

import java.time.Instant;
import java.util.Objects;

public class OpportunityOpenedEvent implements OpportunityStageChangedEvent {

    private final OpportunityId opportunityId;
    private final AccountId accountId;
    private final String name;
    private final Instant endDate;

    public OpportunityOpenedEvent(OpportunityId opportunityId, AccountId accountId, String name, Instant endDate) {
        this.opportunityId = opportunityId;
        this.accountId = accountId;
        this.name = name;
        this.endDate = endDate;
    }

    @Override
    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    @Override
    public OpportunityStage getStage() {
        return OpportunityStage.RFP;
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
        OpportunityOpenedEvent that = (OpportunityOpenedEvent) o;
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
        return "OpportunityOpenedEvent{" +
                "opportunityId=" + opportunityId +
                ", stage=" + OpportunityStage.RFP +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", endDate=" + endDate +
                '}';
    }
}
