package io.axoniq.opportunity.coreapi.account;

import java.time.Instant;

public record DealInsight(AccountId accountId,
                          String accountName,
                          int openDeals,
                          double totalWeighedOpenDealAmount,
                          Instant lastDealDate) {

}
