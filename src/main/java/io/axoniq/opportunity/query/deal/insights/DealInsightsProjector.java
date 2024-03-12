package io.axoniq.opportunity.query.deal.insights;

import io.axoniq.opportunity.coreapi.account.AccountCreatedEvent;
import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.account.DealInsight;
import io.axoniq.opportunity.coreapi.account.FindAllDealInsightsQuery;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunitiesByAccountIdQuery;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedLostEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityClosedWonEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import io.axoniq.opportunity.coreapi.opportunity.OpportunitySummary;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Profile("query")
@Service
@ProcessingGroup("deal-insights")
class DealInsightsProjector {

    private final DealInsightsRepository repository;
    private final QueryGateway queryGateway;

    DealInsightsProjector(DealInsightsRepository repository, QueryGateway queryGateway) {
        this.repository = repository;
        this.queryGateway = queryGateway;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        DealInsightsView view = new DealInsightsView();
        view.setAccountId(event.accountId().toString());
        view.setName(event.name());

        repository.save(view);
    }

    @EventHandler
    public void on(OpportunityOpenedEvent event) {
        repository.findById(event.accountId().toString())
                  .map(view -> view.addOpportunityId(event.opportunityId())
                                   .incrementOpenDealCount());
    }

    @EventHandler
    public void on(OpportunityClosedLostEvent event) {
        repository.findByOpportunityIdsContains(event.opportunityId().toString())
                  .map(DealInsightsView::decrementOpenDealCount);
    }

    @EventHandler
    public void on(OpportunityClosedWonEvent event, @Timestamp Instant eventTimestamp) {
        repository.findByOpportunityIdsContains(event.opportunityId().toString())
                  .map(view -> {
                      view.decrementOpenDealCount();
                      view.setLastDealDate(eventTimestamp);
                      return view;
                  });
    }

    @QueryHandler
    public List<DealInsight> handle(FindAllDealInsightsQuery query) {
        return repository.findAll()
                         .stream()
                         .map(view -> new DealInsight(
                                 new AccountId(view.getAccountId()),
                                 view.getOpenDeals(),
                                 totalWeightedOpenDealAmountFor(new AccountId(view.getAccountId())),
                                 view.getLastDealDate()
                         ))
                         .collect(Collectors.toList());
    }

    private double totalWeightedOpenDealAmountFor(AccountId accountId) {
        try {
            return queryGateway.query(new FindOpportunitiesByAccountIdQuery(accountId),
                                      ResponseTypes.multipleInstancesOf(OpportunitySummary.class))
                               .get(500, TimeUnit.MILLISECONDS)
                               .stream()
                               .filter(summary -> summary.stage() != OpportunityStage.CLOSED_WON)
                               .filter(summary -> summary.stage() != OpportunityStage.CLOSED_LOST)
                               .map(OpportunitySummary::weightedValue)
                               .reduce(Double::sum)
                               .orElse(0.0);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return 0.0;
        }
    }
}
