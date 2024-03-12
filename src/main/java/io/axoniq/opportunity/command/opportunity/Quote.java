package io.axoniq.opportunity.command.opportunity;


import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.quote.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.ProductRemovedFromQuoteEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteId;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteRejectedEvent;
import io.axoniq.opportunity.coreapi.opportunity.quote.RejectQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.RemoveProductFromQuoteCommand;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

class Quote {

    @EntityId
    private final QuoteId quoteId;
    private final OpportunityId opportunityId;
    private final String name;
    private final List<ProductId> reservedProducts;
    private int value;
    private boolean approved;

    Quote(QuoteId quoteId, OpportunityId opportunityId, String name, List<ProductLineItem> products) {
        this.quoteId = quoteId;
        this.opportunityId = opportunityId;
        this.name = name;
        this.reservedProducts = products.stream()
                                        .map(ProductLineItem::productId)
                                        .collect(Collectors.toList());
        this.value = products.stream()
                             .map(ProductLineItem::value)
                             .reduce(Integer::sum)
                             .orElse(0);
    }

    @CommandHandler
    public void handle(AddProductToQuoteCommand command) {
        apply(new ProductAddedToQuoteEvent(opportunityId, quoteId, command.product()));
    }

    @CommandHandler
    public void handle(RemoveProductFromQuoteCommand command) {
        ProductId productId = command.productId();
        if (!reservedProducts.contains(productId)) {
            // Ignore this command as we did not reserve this product.
            return;
        }
        apply(new ProductRemovedFromQuoteEvent(opportunityId, quoteId, productId, command.reason()));
    }

    @CommandHandler
    public void handle(RejectQuoteCommand command) {
        apply(new QuoteRejectedEvent(opportunityId, quoteId));
    }

    @EventSourcingHandler
    public void on(QuoteApprovedEvent event) {
        this.approved = true;
    }

    @EventSourcingHandler
    public void on(ProductAddedToQuoteEvent event) {
        this.reservedProducts.add(event.product().productId());
        this.value += event.product().value();
    }

    @EventSourcingHandler
    public void on(ProductRemovedFromQuoteEvent event) {
        this.reservedProducts.remove(event.productId());
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    public String getName() {
        return name;
    }

    public boolean isApproved() {
        return approved;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        return value == quote.value && approved == quote.approved
                && Objects.equals(quoteId, quote.quoteId)
                && Objects.equals(opportunityId, quote.opportunityId)
                && Objects.equals(name, quote.name)
                && Objects.equals(reservedProducts, quote.reservedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quoteId, opportunityId, name, reservedProducts, value, approved);
    }
}
