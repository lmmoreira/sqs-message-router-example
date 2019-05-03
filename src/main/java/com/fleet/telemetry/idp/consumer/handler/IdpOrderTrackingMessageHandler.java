package com.fleet.telemetry.idp.consumer.handler;

import static java.util.Objects.requireNonNull;

import com.amazonaws.services.sqs.model.Message;
import com.fleet.telemetry.idp.model.converter.FleetTelemetryDriverDTOConverter;
import com.fleet.telemetry.idp.service.FleetTelemetryIdpService;
import com.lorem.logistics.event.amazon.sqs.handler.AbstractSingleAmazonSQSMessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("idpOrderTrackingMessageHandler")
public class IdpOrderTrackingMessageHandler extends AbstractSingleAmazonSQSMessageHandler {

    @Autowired
    private FleetTelemetryDriverDTOConverter converter;

    @Autowired
    private FleetTelemetryIdpService fleetTelemetryIdpService;

    @Override
    protected Mono<Void> handleMessage(final Message message) {
        return fleetTelemetryIdpService.send(requireNonNull(converter.convert(message.getBody()))).then();
    }
}
