package io.axoniq.opportunity.query.account;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class AccountView {

    @Id
    private String accountId;
    private int openDeals;
    // TODO Emmett - How do I calculate the total weighed open deadl amount?
    private int totalWeighedOpenDealAmount;
    // TODO Emmett - When would the lastDealDate be updated?
    private Instant lastDealDate;

    public AccountView(String accountId) {
        this.accountId = accountId;
        this.openDeals = 0;
        this.totalWeighedOpenDealAmount = 0;
        this.lastDealDate = null;
    }

    public AccountView() {
        // Required no-arg constructor
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getOpenDeals() {
        return openDeals;
    }

    public void incrementOpenDealCount() {
        openDeals++;
    }

    public void decrementOpenDealCount() {
        openDeals--;
    }

    public int getTotalWeighedOpenDealAmount() {
        return totalWeighedOpenDealAmount;
    }

    public void setTotalWeighedOpenDealAmount(int totalWeighedOpenDealAmount) {
        this.totalWeighedOpenDealAmount = totalWeighedOpenDealAmount;
    }

    public Instant getLastDealDate() {
        return lastDealDate;
    }

    public void setLastDealDate(Instant lastDealDate) {
        this.lastDealDate = lastDealDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountView that = (AccountView) o;
        return openDeals == that.openDeals
                && totalWeighedOpenDealAmount == that.totalWeighedOpenDealAmount
                && Objects.equals(accountId, that.accountId)
                && Objects.equals(lastDealDate, that.lastDealDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, openDeals, totalWeighedOpenDealAmount, lastDealDate);
    }

    @Override
    public String toString() {
        return "AccountView{" +
                "accountId='" + accountId + '\'' +
                ", openDeals=" + openDeals +
                ", totalWeighedOpenDealAmount=" + totalWeighedOpenDealAmount +
                ", lastDealDate=" + lastDealDate +
                '}';
    }
}
