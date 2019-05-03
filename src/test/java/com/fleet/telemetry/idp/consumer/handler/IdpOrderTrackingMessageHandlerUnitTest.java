package com.fleet.telemetry.idp.consumer.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.amazonaws.services.sqs.model.Message;
import com.fleet.telemetry.idp.BaseTest;
import com.fleet.telemetry.idp.factory.FleetTelemetryDTOFactory;
import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import com.fleet.telemetry.idp.model.converter.FleetTelemetryDriverDTOConverter;
import com.fleet.telemetry.idp.service.FleetTelemetryIdpService;
import com.lorem.logistics.event.amazon.sqs.handler.AmazonSQSMessageHandler;
import com.lorem.logistics.event.amazon.sqs.handler.MessageDecorator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.netty.channel.ConnectTimeoutException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class IdpOrderTrackingMessageHandlerUnitTest extends BaseTest {

    @Autowired
    @Qualifier("idpOrderTrackingMessageHandler")
    private AmazonSQSMessageHandler sqsMessageHandler;

    @Autowired
    private FleetTelemetryDTOFactory telemetryDTOFactory;

    @Autowired
    @MockBean
    private FleetTelemetryDriverDTOConverter converter;

    @Autowired
    @MockBean
    private FleetTelemetryIdpService fleetTelemetryIdpService;

    @Before
    public void setup() {
        when(converter.convert(anyString())).thenReturn(telemetryDTOFactory.getDriverDTO());
    }

    @Test
    public void testExpectTrueForSuccessSending() {
        when(fleetTelemetryIdpService.send(any(FleetTelemetryDriverDTO.class)))
                .thenReturn(Flux.just(HttpStatus.ACCEPTED));

        final Message message = new Message().withBody("Useless body");
        final List<Message> messages = Collections.singletonList(message);
        final Flux<List<MessageDecorator>> flux = sqsMessageHandler.handle(messages);

        StepVerifier.create(flux)
                .assertNext(decorators -> {
                    assertEquals(messages, decorators.stream().map(MessageDecorator::getMessage).collect(Collectors.toList()));
                    decorators.forEach(decorator -> assertTrue(decorator.isSuccessfullyProcessed()));
                })
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscardedExactly(HttpStatus.ACCEPTED)
                .hasNotDroppedElements()
                .hasNotDroppedErrors();
    }

    @Test
    public void testExpectFalseWhenErrorOccurWhileSending() {
        when(fleetTelemetryIdpService.send(any(FleetTelemetryDriverDTO.class)))
                .thenReturn(Flux.just(HttpStatus.ACCEPTED).concatWith(Flux.error(new ConnectTimeoutException(""))));

        final Message message = new Message().withBody("Useless body");
        final List<Message> messages = Collections.singletonList(message);
        final Flux<List<MessageDecorator>> flux = sqsMessageHandler.handle(messages);

        StepVerifier.create(flux)
                .expectNext(List.of(MessageDecorator.withFail(message)))
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscardedExactly(HttpStatus.ACCEPTED)
                .hasOperatorErrors(1);
    }
}
