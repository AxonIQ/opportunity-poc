package io.axoniq.opportunity.query.opportunity;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Objects;

@Entity
class OpportunityView {

    @Id
    private String opportunityId;
    private String accountId;
    @Enumerated(EnumType.STRING)
    private OpportunityStage stage;
    private String name;
    private int value;
    private Instant endDate;

    public OpportunityView() {
        // Required no-arg constructor
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public OpportunityStage getStage() {
        return stage;
    }

    public void setStage(OpportunityStage stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpportunityView that = (OpportunityView) o;
        return value == that.value
                && Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(accountId, that.accountId)
                && stage == that.stage
                && Objects.equals(name, that.name)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, accountId, stage, name, value, endDate);
    }

    @Override
    public String toString() {
        return "OpportunityView{" +
                "opportunityId='" + opportunityId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", stage=" + stage +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", endDate=" + endDate +
                '}';
    }
}
