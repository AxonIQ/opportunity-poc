package io.axoniq.opportunity.query.deal.insights;

import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunitiesByAccountIdQuery;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunitiesInStageQuery;
import io.axoniq.opportunity.coreapi.opportunity.FindOpportunityByNameQuery;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStageChangedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunitySummary;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@ProcessingGroup("deal-insights")
class OpportunityProjector {

    private final OpportunityRepository repository;

    OpportunityProjector(OpportunityRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OpportunityOpenedEvent event) {
        repository.save(new OpportunityView(event.opportunityId().toString(),
                                            event.accountId().toString(),
                                            event.name(),
                                            event.value(),
                                            event.endDate()));
    }

    @EventHandler
    public void on(OpportunityStageChangedEvent event) {
        repository.findById(event.opportunityId().toString())
                  .map(opportunityView -> {
                      opportunityView.setStage(event.stage());
                      return opportunityView;
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
                view.getValue(),
                view.getEndDate()
        );
    }
}
