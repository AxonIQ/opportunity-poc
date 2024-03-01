package io.axoniq.opportunity.controller;

import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.account.CreateAccountCommand;
import io.axoniq.opportunity.coreapi.opportunity.OpenOpportunityCommand;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import io.axoniq.opportunity.coreapi.opportunity.quote.AddProductToQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.ApproveQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.PitchQuoteCommand;
import io.axoniq.opportunity.coreapi.opportunity.quote.QuoteId;
import io.axoniq.opportunity.coreapi.opportunity.quote.RejectQuoteCommand;
import io.axoniq.opportunity.coreapi.product.ProductId;
import io.axoniq.opportunity.coreapi.product.ProductLineItem;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opportunity")
class OpportunityController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    OpportunityController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/create/account/{name}")
    public CompletableFuture<String> createAccount(@PathVariable("name") String name) {
        return commandGateway.send(new CreateAccountCommand(new AccountId(), name));
    }

    @PostMapping("/open")
    public CompletableFuture<String> openOpportunity(@RequestBody OpenOpportunityDto dto) {
        return commandGateway.send(new OpenOpportunityCommand(new OpportunityId(),
                                                              new AccountId(dto.accountId()),
                                                              dto.name(),
                                                              dto.value(),
                                                              dto.endDate()));
    }

    record OpenOpportunityDto(String accountId, String name, int value, Instant endDate) {

    }

    @PostMapping("/pitch")
    public CompletableFuture<String> pitchQuote(@RequestBody PitchQuoteDto dto) {
        return commandGateway.send(new PitchQuoteCommand(new OpportunityId(dto.opportunityId()),
                                                         dto.name(),
                                                         dto.validUntil(),
                                                         dto.products()
                                                            .stream()
                                                            .map(OpportunityController::fromDto)
                                                            .collect(Collectors.toList())));
    }

    record PitchQuoteDto(String opportunityId,
                         String name,
                         Instant validUntil,
                         List<ProductLineItemDto> products) {

    }

    @PostMapping("/{opportunityId}/quote/{quoteId}/add")
    public CompletableFuture<Void> addProductToQuote(@PathVariable("opportunityId") String opportunityId,
                                                     @PathVariable("quoteId") String quoteId,
                                                     @RequestBody ProductLineItemDto product) {
        return commandGateway.send(new AddProductToQuoteCommand(new OpportunityId(opportunityId),
                                                                new QuoteId(quoteId),
                                                                fromDto(product)));
    }

    record ProductLineItemDto(ProductId productId,
                              int catalogId,
                              int quantity,
                              int amount) {

    }

    static ProductLineItem fromDto(ProductLineItemDto dto) {
        return new ProductLineItem(dto.productId(), dto.catalogId(), dto.quantity(), dto.amount());
    }

    @PostMapping("/{opportunityId}/quote/{quoteId}/approve")
    public CompletableFuture<Void> approveQuote(@PathVariable("opportunityId") String opportunityId,
                                                @PathVariable("quoteId") String quoteId) {
        return commandGateway.send(new ApproveQuoteCommand(new OpportunityId(opportunityId),
                                                           new QuoteId(quoteId)));
    }

    @PostMapping("/{opportunityId}/quote/{quoteId}/reject")
    public CompletableFuture<Void> rejectQuote(@PathVariable("opportunityId") String opportunityId,
                                               @PathVariable("quoteId") String quoteId) {
        return commandGateway.send(new RejectQuoteCommand(new OpportunityId(opportunityId),
                                                          new QuoteId(quoteId)));
    }
}
