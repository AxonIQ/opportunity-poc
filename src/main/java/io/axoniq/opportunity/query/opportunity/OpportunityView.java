package io.axoniq.opportunity.query.opportunity;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
class OpportunityView {

    @Id
    private String opportunityId;
    @Enumerated(EnumType.STRING)
    private OpportunityStage stage;
    private String accountId;
    private String name;
    private Instant endDate;

    public OpportunityView(String opportunityId, String accountId, String name, Instant endDate) {
        this.opportunityId = opportunityId;
        this.stage = OpportunityStage.RFP;
        this.accountId = accountId;
        this.name = name;
        this.endDate = endDate;
    }

    public OpportunityView() {
        // Required no-arg constructor
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public OpportunityStage getStage() {
        return stage;
    }

    public void setStage(OpportunityStage stage) {
        this.stage = stage;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public Instant getEndDate() {
        return endDate;
    }
}
