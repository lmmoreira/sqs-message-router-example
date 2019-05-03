package com.fleet.telemetry.idp.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Setting up localstack in a way that we can run it locally.
 */
@Configuration
@Profile("local")
@Primary
public class LocalstackConfig {

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${localstack.sqs.url}")
    private String localStackSqsUrl;

    @Bean("sqsAsyncClientLocal")
    @Primary
    public AmazonSQS sqsAsyncClient() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackSqsUrl, awsRegion))
                .build();
    }

}
