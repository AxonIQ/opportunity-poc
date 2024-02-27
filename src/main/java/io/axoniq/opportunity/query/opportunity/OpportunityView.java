package io.axoniq.opportunity.query.opportunity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class OpportunityView {

    @Id
    private String identifier;

}
