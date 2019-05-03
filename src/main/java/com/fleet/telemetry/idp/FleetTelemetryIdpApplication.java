package com.fleet.telemetry.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Fleet Telemetry IDP application is responsible for handle messages from a SQS queue containing tracks,
 * process current orders and send them to IDP (lorem Delivery Platform) via HTTP request.
 */
@SpringBootApplication
public class FleetTelemetryIdpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetTelemetryIdpApplication.class, args);
    }

}

