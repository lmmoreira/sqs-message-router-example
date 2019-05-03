# README #

Fleet Telemetry IDP project is responsible for handle messages from a SQS queue containing trackings,
process current orders and send them to IDP DES (lorem Delivery Platform) via HTTP request.

## Technologies and Dependencies ##

* Async Communication
    * SQS
* Java Frameworks
    * Spring Boot
    * Reactor

## Build and deploy ##

Build and deploy are made via [Jenkins job](http://dev-jenkins1.dc.lorem.com.br:8080/view/Logistics/job/telemetry/job/fleet-telemetry-idp-pipeline/).

## Developing locally using `localstack` ##

Locally we use `localstack` to simulate AWS services. Before start, make sure you have
[lorem's local environment project](https://bitbucket.org/lorem/lorem-delivery-platform-local-environment/src/master/) up and running on your development machine
following its instructions.

The SQS queue required for running this application is configured on project's above.

### Running this application on profile `local` ###

To execute the project on your IDE, it's necessary to configure the environment variable `spring.profiles.active=local`.
Running with Maven, for example, works as follow:
```
mvn spring-boot:run -Dspring.profiles.active=local
```# sqs-message-router-example
