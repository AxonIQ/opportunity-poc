package io.axoniq.opportunity.coreapi.opportunity;

import org.axonframework.commandhandling.CommandExecutionException;

/**
 * An Opportunity can most definitely have several Quotes, but for simplicity of the demo only a single quote is allowed
 * at a given time.
 */
public class OpportunityAlreadyHasPendingQuoteException extends CommandExecutionException {

    public OpportunityAlreadyHasPendingQuoteException(OpportunityId opportunityId) {
        super("Opportunity [" + opportunityId + "] already has a pending quote. "
                      + "A new quote can only be added once this quote is rejected.", null);
    }
}
