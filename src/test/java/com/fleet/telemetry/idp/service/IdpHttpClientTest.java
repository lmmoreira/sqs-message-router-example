package com.fleet.telemetry.idp.service;

import com.fleet.telemetry.idp.config.TestConfig;
import com.fleet.telemetry.idp.factory.FleetTelemetryDTOFactory;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class IdpHttpClientTest {

    @Autowired
    private IdpHttpClient idpHttpClient;

    @Autowired
    private FleetTelemetryDTOFactory fleetTelemetryDTOFactory;

    @Test
    public void testNotifyWorkerTrackIdp() {
        final Mono<HttpStatus> notifyMono = idpHttpClient.notifyWorkerTrack(fleetTelemetryDTOFactory.getIdpOrderDto());
        StepVerifier.create(notifyMono)
                .assertNext(httpStatus -> Assertions.assertThat(httpStatus.is2xxSuccessful()).isTrue())
                .verifyComplete();
    }
}
