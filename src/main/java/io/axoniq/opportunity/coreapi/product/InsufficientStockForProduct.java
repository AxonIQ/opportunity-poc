package io.axoniq.opportunity.coreapi.product;

import io.axoniq.opportunity.coreapi.product.ProductId;
import org.axonframework.commandhandling.CommandExecutionException;

public class InsufficientStockForProduct extends CommandExecutionException {

    public InsufficientStockForProduct(ProductId productId, int amount) {
        super("Cannot reserve [" + amount + "] for product [" + productId + "] since there is insufficient stock",
              null);
    }
}
