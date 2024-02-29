package io.axoniq.opportunity.coreapi;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

// TODO Emmett - Or call this PitchQuoteCommand, as sellers are pitching a quote (from shared doc)?
public class CreateQuoteCommand {

    @TargetAggregateIdentifier
    private final OpportunityId opportunityId;
    private final String name;
    private final Instant validUntil;
    // TODO Emmett - Are all the products part of the Quote ASAP? Or can they also be added later?
    private final List<ProductLineItem> products;

    public CreateQuoteCommand(OpportunityId opportunityId,
                              String name,
                              Instant validUntil,
                              List<ProductLineItem> products) {
        this.opportunityId = opportunityId;
        this.name = name;
        this.validUntil = validUntil;
        this.products = products;
    }

    public OpportunityId getOpportunityId() {
        return opportunityId;
    }

    public String getName() {
        return name;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public List<ProductLineItem> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateQuoteCommand that = (CreateQuoteCommand) o;
        return Objects.equals(opportunityId, that.opportunityId)
                && Objects.equals(name, that.name)
                && Objects.equals(validUntil, that.validUntil)
                && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunityId, name, validUntil, products);
    }

    @Override
    public String toString() {
        return "CreateQuoteCommand{" +
                "opportunityId=" + opportunityId +
                ", name='" + name + '\'' +
                ", validUntil=" + validUntil +
                ", products=" + products +
                '}';
    }
}
