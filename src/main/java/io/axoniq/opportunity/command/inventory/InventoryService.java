package io.axoniq.opportunity.command.inventory;

import io.axoniq.opportunity.coreapi.product.InsufficientStockForProduct;
import io.axoniq.opportunity.coreapi.product.ProductDoesNotExist;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ReleaseReservationForProductCommand;
import io.axoniq.opportunity.coreapi.product.ReserveProductCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InventoryService {

    // TODO Add means to fill inventory
    private final Map<ProductId, Integer> inventory = new ConcurrentHashMap<>();

    @CommandHandler
    public void handle(ReserveProductCommand command) {
        ProductId productId = command.getProductId();
        if (!inventory.containsKey(productId)) {
            throw new ProductDoesNotExist(productId);
        }

        inventory.computeIfPresent(productId, (id, stock) -> {
            if (stock - command.getAmount() < 0) {
                throw new InsufficientStockForProduct(id, command.getAmount());
            }
            return stock - command.getAmount();
        });
    }

    @CommandHandler
    public void handle(ReleaseReservationForProductCommand command) {
        inventory.computeIfPresent(command.getProductKey(), (productId, stock) -> stock + command.getAmount());
    }
}
