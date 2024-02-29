package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AccountId;
import io.axoniq.opportunity.coreapi.ApproveQuoteCommand;
import io.axoniq.opportunity.coreapi.CreateQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityAlreadyHasApprovedQuoteException;
import io.axoniq.opportunity.coreapi.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.OpportunityPitchedEvent;
import io.axoniq.opportunity.coreapi.OpportunityStage;
import io.axoniq.opportunity.coreapi.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.QuoteCreatedEvent;
import io.axoniq.opportunity.coreapi.QuoteId;
import io.axoniq.opportunity.coreapi.QuoteValidityCannotExceedEndDate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.axoniq.opportunity.coreapi.OpportunityStage.*;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
class Opportunity {

    static final String OPPORTUNITY_ENDED = "Opportunity Ended";

    @AggregateIdentifier
    private OpportunityId opportunityId;
    private OpportunityStage stage;
    private Instant endDate;
    @AggregateMember
    private Map<QuoteId, Quote> quotes;

    public Opportunity() {
        // No-arg constructor required by Axon for Event Sourcing
    }

    public Opportunity(OpportunityId opportunityId,
                       AccountId accountId,
                       String name,
                       Instant endDate) {
        apply(new OpportunityOpenedEvent(opportunityId, accountId, name, endDate));
    }

    @CommandHandler
    public void handle(CreateQuoteCommand command) {
        // TODO Emmett - Should the quote's valid until set a deadline that automatically rejects the quote?
        if (command.getValidUntil().isAfter(endDate)) {
            throw new QuoteValidityCannotExceedEndDate(command.getName(), endDate, opportunityId);
        }
        if (quotes.values().stream().anyMatch(Quote::isApproved)) {
            throw OpportunityAlreadyHasApprovedQuoteException.createNewQuoteException(opportunityId);
        }
        if (quotes.isEmpty()) {
            // TODO Emmett - The first quote moves the Opportunity's stage to PITCHED I assume?
            apply(new OpportunityPitchedEvent(opportunityId));
        }
        apply(new QuoteCreatedEvent(opportunityId, command.getName(), command.getValidUntil(), command.getProducts()));
    }

    @CommandHandler
    public void handle(ApproveQuoteCommand command) {
        if (stage == CLOSED_WON) {
            throw OpportunityAlreadyHasApprovedQuoteException.approveSecondQuoteException(
                    quotes.get(command.getQuoteId()).getName(),
                    opportunityId
            );
        }
        apply(new QuoteApprovedEvent(opportunityId, command.getQuoteId()));
        apply(new OpportunityClosedWonEvent(opportunityId));
    }

    @DeadlineHandler(deadlineName = OPPORTUNITY_ENDED)
    public void on() {
        // TODO Emmett - I assume this event should not be published given a certain state?
        apply(new OpportunityClosedLostEvent(opportunityId));
    }

    @EventSourcingHandler
    public void on(OpportunityOpenedEvent event) {
        // Add explaining comment
        opportunityId = event.getOpportunityId();
        stage = RFP;
        endDate = event.getEndDate();
        quotes = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(QuoteCreatedEvent event) {
        QuoteId quoteId = event.getQuoteId();
        quotes.put(quoteId, new Quote(quoteId, opportunityId, event.getName()));
    }

    @EventSourcingHandler
    public void on(OpportunityPitchedEvent event) {
        // TODO Emmett - Should the PITCHED stage block certain operations?
        this.stage = PITCHED;
    }

    @EventSourcingHandler
    public void on(OpportunityClosedWonEvent event) {
        // TODO Emmett - I assume this stage should ensure other commands are no longer handled?
        this.stage = CLOSED_WON;
    }

    @EventSourcingHandler
    public void on(OpportunityClosedLostEvent event) {
        // TODO Emmett - I assume this stage should ensure other commands are no longer handled?
        this.stage = CLOSED_LOST;
    }
}
