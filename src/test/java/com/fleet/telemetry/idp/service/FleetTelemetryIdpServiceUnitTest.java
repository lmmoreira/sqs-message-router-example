package com.fleet.telemetry.idp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fleet.telemetry.idp.BaseTest;
import com.fleet.telemetry.idp.factory.FleetTelemetryDTOFactory;
import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import com.fleet.telemetry.idp.model.FleetTelemetryIdpOrderDTO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FleetTelemetryIdpServiceUnitTest extends BaseTest {

    @Autowired
    private FleetTelemetryIdpService fleetTelemetryIdpService;

    @Autowired
    private FleetTelemetryDTOFactory fleetTelemetryDTOFactory;

    @Autowired
    @MockBean
    private IdpHttpClient idpHttpClient;

    @Before
    public void setup() {
        when(idpHttpClient.notifyWorkerTrack(any(FleetTelemetryIdpOrderDTO.class)))
                .thenReturn(Mono.just(HttpStatus.OK));
    }

    @Test
    public void testSendValidOrders() {
        final FleetTelemetryDriverDTO.OrderIdDTO validOrder = new FleetTelemetryDriverDTO.OrderIdDTO(1L, "168498");

        final FleetTelemetryDriverDTO telemetryDTO = fleetTelemetryDTOFactory.defaultBuilder()
                .withOrder(validOrder.getOrderId(), validOrder.getExternalId())
                .build();

        StepVerifier.create(fleetTelemetryIdpService.send(telemetryDTO))
                .expectNextCount(2L)
                .expectComplete()
                .verifyThenAssertThat()
                .hasNotDiscardedElements();
    }

    @Test
    public void testVerifyFilteringComplementOrder() {
        final FleetTelemetryDriverDTO.OrderIdDTO complementOrder =
            new FleetTelemetryDriverDTO.OrderIdDTO(1L, "168498#Complement");

        final FleetTelemetryDriverDTO telemetryDTO = fleetTelemetryDTOFactory.defaultBuilder()
                .withOrder(complementOrder.getOrderId(), complementOrder.getExternalId())
                .build();

        final int size = telemetryDTO.getCurrentOrders().size();

        assertEquals("2 current orders are expected.", 2, size);

        // Verify dropping of complement order
        StepVerifier.create(fleetTelemetryIdpService.send(telemetryDTO))
                .expectNextCount(size - 1)
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscardedExactly(complementOrder);
    }

}
