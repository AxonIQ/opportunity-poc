package io.axoniq.opportunity.command.sellers;

import io.axoniq.opportunity.coreapi.account.AccountId;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityAlreadyHasApprovedQuoteException;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityAlreadyHasPendingQuoteException;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityPitchedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import io.axoniq.opportunity.coreapi.opportunity.quote.ApproveQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.PitchQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuotePitchedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteId;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteRejectedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteValidityCannotExceedEndDate;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.axoniq.opportunity.coreapi.opportunity.OpportunityStage.*;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
class Opportunity {

    static final String OPPORTUNITY_ENDED = "Opportunity Ended";
    static final String QUOTE_ENDED = "Quote Ended";

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
    public void handle(PitchQuoteCommand command, DeadlineManager deadlineManager) {
        Instant validUntil = command.validUntil();
        if (validUntil.isAfter(endDate)) {
            throw new QuoteValidityCannotExceedEndDate(command.name(), endDate, opportunityId);
        }
        if (stage == PITCHED) {
            throw new OpportunityAlreadyHasPendingQuoteException(opportunityId);
        }
        if (quotes.values().stream().anyMatch(Quote::isApproved)) {
            throw OpportunityAlreadyHasApprovedQuoteException.createNewQuoteException(opportunityId);
        }

        if (quotes.isEmpty()) {
            apply(new OpportunityPitchedEvent(opportunityId));
        }
        QuoteId quoteId = new QuoteId();
        apply(new QuotePitchedEvent(
                opportunityId, quoteId, command.name(), validUntil, command.products()
        ));
        deadlineManager.schedule(validUntil, QUOTE_ENDED, quoteId);
    }

    @CommandHandler
    public void handle(ApproveQuoteCommand command, DeadlineManager deadlineManager) {
        if (stage == CLOSED_WON) {
            throw OpportunityAlreadyHasApprovedQuoteException.approveSecondQuoteException(
                    quotes.get(command.quoteId()).getName(),
                    opportunityId
            );
        }

        apply(new QuoteApprovedEvent(opportunityId, command.quoteId()));
        apply(new OpportunityClosedWonEvent(opportunityId));
        deadlineManager.cancelAllWithinScope(OPPORTUNITY_ENDED);
    }

    @DeadlineHandler(deadlineName = QUOTE_ENDED)
    public void on(QuoteId quoteId) {
        apply(new QuoteRejectedEvent(opportunityId, quoteId));
    }

    @DeadlineHandler(deadlineName = OPPORTUNITY_ENDED)
    public void on() {
        // TODO Emmett - I assume this event should not be published given a certain state?
        apply(new OpportunityClosedLostEvent(opportunityId));
    }

    @EventSourcingHandler
    public void on(OpportunityOpenedEvent event) {
        opportunityId = event.opportunityId();
        stage = RFP;
        endDate = event.endDate();
        quotes = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(QuotePitchedEvent event) {
        QuoteId quoteId = event.quoteId();
        List<ProductId> productsToReserve = event.products()
                                                 .stream()
                                                 .map(ProductLineItem::getProductId)
                                                 .collect(Collectors.toList());
        quotes.put(quoteId, new Quote(quoteId, opportunityId, event.name(), productsToReserve));
    }

    @EventSourcingHandler
    public void on(OpportunityPitchedEvent event) {
        this.stage = PITCHED;
    }

    @EventSourcingHandler
    public void on(OpportunityClosedWonEvent event) {
        this.stage = CLOSED_WON;
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void on(OpportunityClosedLostEvent event) {
        this.stage = CLOSED_LOST;
        AggregateLifecycle.markDeleted();
    }
}
