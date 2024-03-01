package io.axoniq.opportunity.coreapi.product;

import org.axonframework.commandhandling.RoutingKey;

public record ReleaseReservationForProductCommand(@RoutingKey ProductId productId, Integer amount) {

}
