package io.axoniq.opportunity.query.deal.insights;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Set;

@Entity
class DealInsightsView {

    @Id
    private String accountId;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> opportunityIds;
    private int openDeals;
    private Instant lastDealDate;

    public DealInsightsView() {
        // Required no-arg constructor
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getOpportunityIds() {
        return opportunityIds;
    }

    public DealInsightsView addOpportunityId(OpportunityId opportunityId) {
        opportunityIds.add(opportunityId.toString());
        return this;
    }

    public void setOpportunityIds(Set<String> opportunitIds) {
        this.opportunityIds = opportunitIds;
    }

    public int getOpenDeals() {
        return openDeals;
    }

    public DealInsightsView incrementOpenDealCount() {
        openDeals++;
        return this;
    }

    public DealInsightsView decrementOpenDealCount() {
        openDeals--;
        return this;
    }

    public void setOpenDeals(int openDeals) {
        this.openDeals = openDeals;
    }

    public Instant getLastDealDate() {
        return lastDealDate;
    }

    public void setLastDealDate(Instant lastDealDate) {
        this.lastDealDate = lastDealDate;
    }
}
