package io.axoniq.opportunity.command;

import io.axoniq.opportunity.coreapi.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.OpportunityId;
import io.axoniq.opportunity.coreapi.ProductAddedToQuoteEvent;
import io.axoniq.opportunity.coreapi.ProductId;
import io.axoniq.opportunity.coreapi.ProductRemovedFromQuoteEvent;
import io.axoniq.opportunity.coreapi.QuoteApprovedEvent;
import io.axoniq.opportunity.coreapi.QuoteId;
import io.axoniq.opportunity.coreapi.QuoteRejectedEvent;
import io.axoniq.opportunity.coreapi.RejectQuoteCommand;
import io.axoniq.opportunity.coreapi.RemoveProductFromQuoteCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

class Quote {

    @EntityId
    private final QuoteId quoteId;
    private final OpportunityId opportunityId;
    private final String name;
    private final List<ProductId> reservedProducts;

    // TODO Emmett - How would you envision the "draft" state to be incorporated here? Is it needed for validation?
    private boolean approved;

    Quote(QuoteId quoteId, OpportunityId opportunityId, String name) {
        this.quoteId = quoteId;
        this.opportunityId = opportunityId;
        this.name = name;
        this.reservedProducts = new ArrayList<>();
    }

    // TODO this command needs to be pre-validated before sending, as it has an inventory requirement
    @CommandHandler
    public void handle(AddProductToQuoteCommand command) {
        apply(new ProductAddedToQuoteEvent(opportunityId, quoteId, command.getProductId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(RemoveProductFromQuoteCommand command) {
        ProductId productId = command.getProductId();
        if (!reservedProducts.contains(productId)) {
            // Ignore this command as we did not reserve this product.
            return;
        }
        apply(new ProductRemovedFromQuoteEvent(opportunityId, quoteId, productId, command.getReason()));
    }

    @EventSourcingHandler
    public void on(QuoteApprovedEvent event) {
        this.approved = true;
    }

    @EventSourcingHandler
    public void on(ProductAddedToQuoteEvent event) {
        this.reservedProducts.add(event.getProductId());
    }

    @EventSourcingHandler
    public void on(ProductRemovedFromQuoteEvent event) {
        this.reservedProducts.remove(event.getProductId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        return approved == quote.approved
                && Objects.equals(opportunityId, quote.opportunityId)
                && Objects.equals(quoteId, quote.quoteId)
                && Objects.equals(name, quote.name)
                && Objects.equals(reservedProducts, quote.reservedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, name, reservedProducts, approved);
    }
}
