package io.axoniq.opportunity.coreapi.account;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateAccountCommand(@TargetAggregateIdentifier AccountId accountId, String name) {

}
