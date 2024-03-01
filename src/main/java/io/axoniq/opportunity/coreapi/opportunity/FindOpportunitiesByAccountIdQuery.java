package io.axoniq.opportunity.coreapi.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;

public record FindOpportunitiesByAccountIdQuery(AccountId accountId) {

}
