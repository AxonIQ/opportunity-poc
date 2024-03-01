package io.axoniq.opportunity.coreapi.opportunity;

public enum OpportunityStage {
    RFP(0.25), PITCHED(0.50), CLOSED_WON(1.0), CLOSED_LOST(0.0);

    private final double winProbability;

    OpportunityStage(double winProbability) {
        this.winProbability = winProbability;
    }

    public double winProbability() {
        return winProbability;
    }
}
