package io.axoniq.opportunity.query.account;

import io.axoniq.opportunity.coreapi.account.AccountCreatedEvent;
import io.axoniq.opportunity.coreapi.account.AccountId;
import io.axoniq.opportunity.coreapi.account.AccountSummary;
import io.axoniq.opportunity.coreapi.account.FindAllAccountsQuery;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityOpenedEvent;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStage;
import io.axoniq.opportunity.coreapi.opportunity.OpportunityStageChangedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ProcessingGroup("account-summary")
class AccountProjector {

    private final AccountRepository repository;

    AccountProjector(AccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        repository.save(new AccountView(event.accountId().toString()));
    }

    @EventHandler
    public void on(OpportunityOpenedEvent event, @Timestamp Instant eventTimestampe) {
        repository.findById(event.getAccountId().toString())
                  .map(accountView -> {
                      accountView.incrementOpenDealCount();
                      accountView.setLastDealDate(eventTimestampe);
                      return accountView;
                  });
    }

    // TODO Emmett - Are all tasks triggered through a single Account, or can other Sellers trigger stages too?
    @EventHandler
    public void on(OpportunityStageChangedEvent event, @MetaDataValue("accountId") AccountId accountId) {
        OpportunityStage opportunityStage = event.getStage();
        if (opportunityStage == OpportunityStage.CLOSED_LOST || opportunityStage == OpportunityStage.CLOSED_WON) {
            repository.findById(accountId.toString())
                      .map(accountView -> {
                          accountView.decrementOpenDealCount();
                          return accountView;
                      });
        }
    }

    @QueryHandler
    public List<AccountSummary> handle(FindAllAccountsQuery query) {
        return repository.findAll()
                         .stream()
                         .map(view -> new AccountSummary(
                                 new AccountId(view.getAccountId()),
                                 view.getOpenDeals(),
                                 view.getTotalWeighedOpenDealAmount(),
                                 view.getLastDealDate()
                         ))
                         .collect(Collectors.toList());
    }
}
