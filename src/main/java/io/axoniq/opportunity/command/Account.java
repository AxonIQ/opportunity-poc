package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AccountCreatedEvent;
import io.axoniq.opportunity.coreapi.AccountId;
import io.axoniq.opportunity.coreapi.CreateAccountCommand;
import io.axoniq.opportunity.coreapi.OpenOpportunityCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

@Aggregate
class Account {

    @AggregateIdentifier
    private AccountId accountId;

    public Account() {
        // No-arg constructor required by Axon for Event Sourcing
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void handle(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }

    @CommandHandler
    public void handle(OpenOpportunityCommand command) throws Exception {
        createNew(
                Opportunity.class,
                () -> new Opportunity(command.getOpportunityId(), accountId, command.getName())
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        accountId = event.getAccountId();
    }
}
