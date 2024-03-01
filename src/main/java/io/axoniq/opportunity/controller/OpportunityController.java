package io.axoniq.opportunity.controller;

import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.account.CreateAccountCommand;
import io.axoniq.opportunity.coreapi.opportunity.OpenOpportunityCommand;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

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
}
