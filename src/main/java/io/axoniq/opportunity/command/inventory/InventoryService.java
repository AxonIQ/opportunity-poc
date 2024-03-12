package io.axoniq.opportunity.command.inventory;

import io.axoniq.opportunity.coreapi.product.InsufficientStockForProduct;
import io.axoniq.opportunity.coreapi.product.ProductDoesNotExist;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ReleaseReservationForProductCommand;
import io.axoniq.opportunity.coreapi.product.ReserveProductCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Profile("command")
@Service
@RequestMapping("/inventory")
@RestController
class InventoryService {

    private final Map<ProductId, Integer> inventory = new ConcurrentHashMap<>();

    @CommandHandler
    public void handle(ReserveProductCommand command) {
        ProductId productId = command.productId();
        if (!inventory.containsKey(productId)) {
            throw new ProductDoesNotExist(productId);
        }

        inventory.computeIfPresent(productId, (id, stock) -> {
            if (stock - command.amount() < 0) {
                throw new InsufficientStockForProduct(id, command.amount());
            }
            return stock - command.amount();
        });
    }

    @CommandHandler
    public void handle(ReleaseReservationForProductCommand command) {
        inventory.computeIfPresent(command.productId(), (productId, stock) -> stock + command.amount());
    }

    @PostMapping("/add")
    public void addInventory(@RequestBody ProductDto product) {
        inventory.put(new ProductId(product.productId()), product.amount());
    }

    record ProductDto(String productId, int amount) {

    }
}
