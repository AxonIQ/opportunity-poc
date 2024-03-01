package io.axoniq.opportunity.coreapi.product;

import org.axonframework.commandhandling.RoutingKey;

public record ReserveProductCommand(@RoutingKey ProductId productId, int amount) {

}
