package io.axoniq.opportunity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.lifecycle.Phase;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class AxonConfiguration {

    @Bean
    @Primary
    Serializer serializer() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                                                      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                                                      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return JacksonSerializer.builder()
                                .objectMapper(objectMapper)
                                .defaultTyping()
                                .lenientDeserialization()
                                .build();
    }

    @Bean
    ConfigurerModule enablePsepConfigurerModule() {
        return configurer -> configurer.eventProcessing().usingPooledStreamingEventProcessors();
    }

    @Bean
    LoggingInterceptor<?> loggingInterceptor() {
        return new LoggingInterceptor<>();
    }

    @SuppressWarnings("resource")
    @Bean
    ConfigurerModule loggingInterceptorConfigurerModule(LoggingInterceptor<Message<?>> loggingInterceptor) {
        return configurer -> {
            configurer.onInitialize(config -> config.onStart(Phase.INSTRUCTION_COMPONENTS, () -> {
                CommandBus commandBus = config.commandBus();
                commandBus.registerDispatchInterceptor(loggingInterceptor);
                commandBus.registerHandlerInterceptor(loggingInterceptor);
                EventBus eventBus = config.eventBus();
                eventBus.registerDispatchInterceptor(loggingInterceptor);
                QueryBus queryBus = config.queryBus();
                queryBus.registerDispatchInterceptor(loggingInterceptor);
                queryBus.registerHandlerInterceptor(loggingInterceptor);
            }));
            configurer.eventProcessing()
                      .registerDefaultHandlerInterceptor((c, processorName) -> loggingInterceptor);
        };
    }

    @Bean
    DeadlineManager deadlineManager(org.axonframework.config.Configuration configuration) {
        return SimpleDeadlineManager.builder()
                                    .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
                                    .build();
    }
}
