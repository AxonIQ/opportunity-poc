package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.commandhandling.CommandExecutionException;

import java.time.Instant;

public class QuoteValidityCannotExceedEndDate extends CommandExecutionException {

    public QuoteValidityCannotExceedEndDate(String quoteName,
                                            Instant opportunityEndDate,
                                            OpportunityId opportunityId) {
        super("Quote [" + quoteName + "] validity date cannot exceed the end date [" + opportunityEndDate.toString()
                      + "] of opportunity [" + opportunityId.toString() + "]", null);
    }
}
