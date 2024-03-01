package io.axoniq.opportunity.coreapi.opportunity.quote;

import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class AddProductToQuoteCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final QuoteId quoteId;
    private final ProductLineItem product;

    public AddProductToQuoteCommand(OpportunityId opportunityId, QuoteId quoteId, ProductLineItem product) {
        this.opportunityId = opportunityId;
        this.quoteId = quoteId;
        this.product = product;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public QuoteId getQuoteId() {
        return quoteId;
    }

    public ProductLineItem getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddProductToQuoteCommand that = (AddProductToQuoteCommand) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(quoteId, that.quoteId)
                && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, quoteId, product);
    }

    @Override
    public String toString() {
        return "AddProductToQuoteCommand{" +
                "opportunityId=" + opportunityId +
                ", quoteId=" + quoteId +
                ", product=" + product +
                '}';
    }
}
