package io.axoniq.opportunity.coreapi;

import org.axonframework.commandhandling.CommandExecutionException;

public class OpportunityAlreadyHasApprovedQuoteException extends CommandExecutionException {

    public OpportunityAlreadyHasApprovedQuoteException(String quoteName, OpportunityId opportunityId) {
        super("Cannot approve quote [" + quoteName + "] as opportunity [" + opportunityId
                      + "] already has an approved quote", null);
    }
}
