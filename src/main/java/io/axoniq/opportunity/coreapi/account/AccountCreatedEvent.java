package io.axoniq.opportunity.coreapi.account;

public record AccountCreatedEvent(AccountId accountId, String name) {
}
