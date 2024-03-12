package io.axoniq.opportunity.query.opportunity;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile("query")
interface OpportunityRepository extends JpaRepository<OpportunityView, String> {

    List<OpportunityView> findAllByAccountId(String accountId);

    Optional<OpportunityView> findByName(String name);

    List<OpportunityView> findAllByStage(OpportunityStage stage);
}
