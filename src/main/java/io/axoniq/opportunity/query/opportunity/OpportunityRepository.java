package io.axoniq.opportunity.query.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;

interface OpportunityRepository extends JpaRepository<OpportunityView, String> {

}
