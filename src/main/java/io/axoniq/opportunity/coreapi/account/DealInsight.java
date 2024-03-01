package io.axoniq.opportunity.coreapi.account;

import java.time.Instant;

public record DealInsight(AccountId accountId,
                          int openDeals,
                          double totalWeighedOpenDealAmount,
                          Instant lastDealDate) {

}
