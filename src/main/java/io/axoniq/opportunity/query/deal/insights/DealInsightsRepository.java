package io.axoniq.opportunity.query.deal.insights;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface DealInsightsRepository extends JpaRepository<DealInsightsView, String> {

    Optional<DealInsightsView> findByOpportunityIdsContains(String opportunityId);
}
