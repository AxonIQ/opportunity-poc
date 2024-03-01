package io.axoniq.opportunity.coreapi.account;

import java.time.Instant;
import java.util.Objects;

public class AccountSummary {

    private final AccountId accountId;
    private final int openDeals;
    private final int totalWeighedOpenDealAmount;
    private final Instant lastDealDate;

    public AccountSummary(AccountId accountId, int openDeals, int totalWeighedOpenDealAmount, Instant lastDealDate) {
        this.accountId = accountId;
        this.openDeals = openDeals;
        this.totalWeighedOpenDealAmount = totalWeighedOpenDealAmount;
        this.lastDealDate = lastDealDate;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public int getOpenDeals() {
        return openDeals;
    }

    public int getTotalWeighedOpenDealAmount() {
        return totalWeighedOpenDealAmount;
    }

    public Instant getLastDealDate() {
        return lastDealDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountSummary that = (AccountSummary) o;
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
        return "AccountSummary{" +
                "accountId=" + accountId +
                ", openDeals=" + openDeals +
                ", totalWeighedOpenDealAmount=" + totalWeighedOpenDealAmount +
                ", lastDealDate=" + lastDealDate +
                '}';
    }
}
