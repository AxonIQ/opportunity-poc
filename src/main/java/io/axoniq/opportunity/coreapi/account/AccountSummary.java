package io.axoniq.opportunity.coreapi.account;

import java.time.Instant;

public record AccountSummary(AccountId accountId, int openDeals, int totalWeighedOpenDealAmount, Instant lastDealDate) {

}
