package io.axoniq.opportunity.query.notifications;

import io.axoniq.opportunity.coreapi.opportunity.quote.ProductRemovedFromQuoteEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Profile("query")
@Service
@ProcessingGroup("notifications")
class ProcessNotifier {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @EventHandler
    public void on(ProductRemovedFromQuoteEvent event) {
        logger.info("Product [{}] has been removed from quote [{}] attached to opportunity [{}]",
                    event.productId(), event.quoteId(), event.opportunityId());
    }
}
