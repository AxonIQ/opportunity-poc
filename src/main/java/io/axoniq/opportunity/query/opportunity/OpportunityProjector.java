package io.axoniq.opportunity.query.opportunity;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStageChangedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("opportunity")
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
}
