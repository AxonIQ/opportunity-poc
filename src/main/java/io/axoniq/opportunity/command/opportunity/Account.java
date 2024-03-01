package io.axoniq.opportunity.command.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountCreatedEvent;
import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.account.CreateAccountCommand;
import io.axoniq.opportunity.coreapi.opportunity.OpenOpportunityCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

/**
 * Accounts are constructed by Sellers. Although a Seller can have several Accounts, this demo does not reflect this for
 * simplicity.
 */
@Aggregate
class Account {

    static final String OPPORTUNITY_ENDED = "Opportunity Ended";

    @AggregateIdentifier
    private AccountId accountId;

    public Account() {
        // No-arg constructor required by Axon for Event Sourcing
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void handle(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.accountId(), command.name()));
    }

    @CommandHandler
    public void handle(OpenOpportunityCommand command,
                       DeadlineManager deadlineManager) throws Exception {
        createNew(
                Opportunity.class,
                () -> new Opportunity(
                        command.opportunityId(), accountId, command.name(), command.value(), command.endDate()
                )
        );
        deadlineManager.schedule(command.endDate(), OPPORTUNITY_ENDED);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        accountId = event.accountId();
    }
}
