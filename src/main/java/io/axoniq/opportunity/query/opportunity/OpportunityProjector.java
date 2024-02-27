package io.axoniq.opportunity.query.opportunity;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("opportunity-projector")
class OpportunityProjector {

    private final OpportunityRepository repository;

    OpportunityProjector(OpportunityRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(String event) {

    }
}
