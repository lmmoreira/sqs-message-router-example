package com.fleet.telemetry.idp.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fleet.telemetry.idp.config.properties.EndpointProperties;
import com.lorem.logistics.event.Consumer;
import com.lorem.logistics.event.amazon.sqs.AmazonSQSConsumer;
import com.lorem.logistics.event.amazon.sqs.configuration.GenericAmazonSQSProperties;
import com.lorem.logistics.event.amazon.sqs.handler.AmazonSQSMessageHandler;
import com.lorem.logistics.event.metrics.ConsumerMetricsLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

/**
 * IDP related configurations.
 */
@Configuration
@EnableConfigurationProperties
@EnableScheduling
public class IdpOrderTrackingConfig implements SchedulingConfigurer {

    private static final Duration INTERVAL_METRICS_LOG = Duration.ofSeconds(60);

    @Autowired
    private List<Consumer> consumers;

    @Bean("idpSqsConsumerProperties")
    @ConfigurationProperties(prefix = "idp.order.tracking.consumer")
    public GenericAmazonSQSProperties idpSqsConsumerProperties() {
        return new GenericAmazonSQSProperties();
    }

    @Bean("idpSqsConsumerExecutor")
    public AmazonSQSConsumer idpSqsConsumerExecutor(final AmazonSQS sqsAsyncClient,
                                                    @Qualifier("idpSqsConsumerProperties") final GenericAmazonSQSProperties sqsConsumerProperties,
                                                    @Qualifier("idpOrderTrackingMessageHandler") final AmazonSQSMessageHandler sqsMessageHandler) {
        return new AmazonSQSConsumer(sqsAsyncClient, sqsConsumerProperties, sqsMessageHandler);
    }

    @Bean("idpEndpointProperties")
    @ConfigurationProperties(prefix = "idp.order.tracking.notification")
    public EndpointProperties idpOrderTrackingEndpointProperties() {
        return new EndpointProperties();
    }

    @Bean("idpDesWebClient")
    public WebClient webClient(@Qualifier("idpEndpointProperties") final EndpointProperties endpointProperties) {
        return WebClient.builder().baseUrl(endpointProperties.getBaseUrl()).build();
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedDelayTask(new ConsumerMetricsLogger(consumers), INTERVAL_METRICS_LOG.toMillis());
    }

}
