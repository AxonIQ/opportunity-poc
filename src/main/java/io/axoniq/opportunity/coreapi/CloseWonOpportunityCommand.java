package io.axoniq.opportunity.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class CloseWonOpportunityCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;

    public CloseWonOpportunityCommand(OpportunityId opportunityId) {
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
        CloseWonOpportunityCommand that = (CloseWonOpportunityCommand) o;
        return Objects.equals(opportunityId, that.opportunityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId);
    }

    @Override
    public String toString() {
        return "CloseWonOpportunityCommand{" +
                "opportunityId=" + opportunityId +
                '}';
    }
}
