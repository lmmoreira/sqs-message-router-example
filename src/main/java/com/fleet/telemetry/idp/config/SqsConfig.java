package com.fleet.telemetry.idp.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SQS related configurations.
 */
@Configuration
@EnableCaching
public class SqsConfig {

    @Bean
    public AmazonSQS sqsAsyncClient() {
        return AmazonSQSClientBuilder.defaultClient();
    }

}
