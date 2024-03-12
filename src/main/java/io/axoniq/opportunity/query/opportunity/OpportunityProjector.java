package io.axoniq.opportunity.query.opportunity;

import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunitiesByAccountIdQuery;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunitiesInStageQuery;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunityByNameQuery;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStageChangedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunitySummary;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteApprovedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("query")
@Service
@ProcessingGroup("deal-insights")
class OpportunityProjector {

    private final OpportunityRepository repository;

    OpportunityProjector(OpportunityRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OpportunityOpenedEvent event) {
        OpportunityView view = new OpportunityView();
        view.setOpportunityId(event.opportunityId().toString());
        view.setAccountId(event.accountId().toString());
        view.setStage(OpportunityStage.RFP);
        view.setName(event.name());
        view.setOpportunityValue(event.value());
        view.setEndDate(event.endDate());
        repository.save(view);
    }

    @EventHandler
    public void on(OpportunityStageChangedEvent event) {
        repository.findById(event.opportunityId().toString())
                  .map(view -> {
                      view.setStage(event.stage());
                      return view;
                  });
    }

    @EventHandler
    public void on(QuoteApprovedEvent event) {
        repository.findById(event.opportunityId().toString())
                  .map(view -> {
                      view.setOpportunityValue(event.value());
                      return view;
                  });
    }

    @QueryHandler
    public List<OpportunitySummary> handle(FindOpportunitiesByAccountIdQuery query) {
        return repository.findAllByAccountId(query.accountId().toString())
                         .stream()
                         .map(OpportunityProjector::mapViewToSummary)
                         .collect(Collectors.toList());
    }

    @QueryHandler
    public Optional<OpportunitySummary> handle(FindOpportunityByNameQuery query) {
        return repository.findByName(query.name())
                         .map(OpportunityProjector::mapViewToSummary);
    }

    @QueryHandler
    public List<OpportunitySummary> handle(FindOpportunitiesInStageQuery query) {
        return repository.findAllByStage(query.stage())
                         .stream()
                         .map(OpportunityProjector::mapViewToSummary)
                         .collect(Collectors.toList());
    }

    private static OpportunitySummary mapViewToSummary(OpportunityView view) {
        return new OpportunitySummary(
                new OpportunityId(view.getOpportunityId()),
                new AccountId(view.getAccountId()),
                view.getStage(),
                view.getName(),
                view.getOpportunityValue(),
                view.getEndDate()
        );
    }
}
