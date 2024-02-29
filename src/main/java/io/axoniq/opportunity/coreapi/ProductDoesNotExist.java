package io.axoniq.opportunity.coreapi;

import org.axonframework.commandhandling.CommandExecutionException;

public class ProductDoesNotExist extends CommandExecutionException {

    public ProductDoesNotExist(ProductId productId) {
        super("There is no know product under identifier [" + productId + "]", null);
    }
}
