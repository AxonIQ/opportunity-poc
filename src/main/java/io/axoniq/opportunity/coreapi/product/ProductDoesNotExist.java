package io.axoniq.opportunity.coreapi.product;

import io.axoniq.opportunity.coreapi.product.ProductId;
import org.axonframework.commandhandling.CommandExecutionException;

public class ProductDoesNotExist extends CommandExecutionException {

    public ProductDoesNotExist(ProductId productId) {
        super("There is no know product under identifier [" + productId + "]", null);
    }
}
