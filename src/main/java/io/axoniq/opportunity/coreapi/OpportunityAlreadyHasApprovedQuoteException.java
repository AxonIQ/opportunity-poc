package io.axoniq.opportunity.coreapi;

import org.axonframework.commandhandling.CommandExecutionException;

public class OpportunityAlreadyHasApprovedQuoteException extends CommandExecutionException {

    public OpportunityAlreadyHasApprovedQuoteException(String message) {
        super(message, null);
    }

    public static OpportunityAlreadyHasApprovedQuoteException approveSecondQuoteException(String quoteName, OpportunityId opportunityId) {
        return new OpportunityAlreadyHasApprovedQuoteException(
                "Cannot approve quote [" + quoteName + "] since opportunity [" + opportunityId + "] already has an approved quote"
        );
    }

    public static OpportunityAlreadyHasApprovedQuoteException createNewQuoteException(OpportunityId opportunityId) {
        return new OpportunityAlreadyHasApprovedQuoteException(
                "Cannot create another quote since opportunity [" + opportunityId + "] already has an approved quote"
        );
    }
}
