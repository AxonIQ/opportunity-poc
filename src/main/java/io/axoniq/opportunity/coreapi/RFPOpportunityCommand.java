package io.axoniq.opportunity.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

// TODO what does RFP stand for?
public class RFPOpportunityCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;

    public RFPOpportunityCommand(OpportunityId opportunityId) {
        this.opportunityId = opportunityId;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RFPOpportunityCommand that = (RFPOpportunityCommand) o;
        return Objects.equals(opportunityId, that.opportunityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId);
    }

    @Override
    public String toString() {
        return "RFPOpportunityCommand{" +
                "opportunityId=" + opportunityId +
                '}';
    }
}
