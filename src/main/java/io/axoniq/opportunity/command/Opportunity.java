package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AccountId;
import io.axoniq.opportunity.coreapi.CloseLostOpportunityCommand;
import io.axoniq.opportunity.coreapi.CloseWonOpportunityCommand;
import io.axoniq.opportunity.coreapi.CreateQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.OpportunityPitchedEvent;
import io.axoniq.opportunity.coreapi.OpportunityRFPdEvent;
import io.axoniq.opportunity.coreapi.PitchOpportunityCommand;
import io.axoniq.opportunity.coreapi.QuoteCreatedEvent;
import io.axoniq.opportunity.coreapi.QuoteValidityCannotExceedEndDate;
import io.axoniq.opportunity.coreapi.RFPOpportunityCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
class Opportunity {

    @AggregateIdentifier
    private OpportunityId opportunityId;
    private Instant endDate;
    @AggregateMember
    private List<Quote> quotes;

    public Opportunity() {
        // No-arg constructor required by Axon for Event Sourcing
    }

    public Opportunity(OpportunityId opportunityId,
                       AccountId accountId,
                       String name) {
        apply(new OpportunityOpenedEvent(opportunityId, accountId, name));
    }

    @CommandHandler
    public void handle(CreateQuoteCommand command) {
        if (command.getValidUntil().isAfter(endDate)) {
            throw new QuoteValidityCannotExceedEndDate(command.getName(), endDate, opportunityId);
        }
        apply(new QuoteCreatedEvent(opportunityId, command.getName(), command.getValidUntil(), command.getProducts()));
    }

    @CommandHandler
    public void handle(RFPOpportunityCommand command) {
        apply(new OpportunityRFPdEvent(opportunityId));
    }

    @CommandHandler
    public void handle(PitchOpportunityCommand command) {
        apply(new OpportunityPitchedEvent(opportunityId));
    }

    @CommandHandler
    public void handle(CloseWonOpportunityCommand command) {
        apply(new OpportunityClosedWonEvent(opportunityId));
    }

    @CommandHandler
    public void handle(CloseLostOpportunityCommand command) {
        apply(new OpportunityClosedLostEvent(opportunityId));
    }

    @EventSourcingHandler
    public void on(OpportunityOpenedEvent event) {
        opportunityId = event.getOpportunityId();
    }
}
