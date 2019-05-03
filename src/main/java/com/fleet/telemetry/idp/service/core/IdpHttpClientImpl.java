package com.fleet.telemetry.idp.service.core;

import com.fleet.telemetry.idp.config.properties.EndpointProperties;
import com.fleet.telemetry.idp.exception.FleetTelemetryIdpException;
import com.fleet.telemetry.idp.model.FleetTelemetryIdpOrderDTO;
import com.fleet.telemetry.idp.service.IdpHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import static java.time.Duration.ofMillis;

@Service
public class IdpHttpClientImpl implements IdpHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdpHttpClientImpl.class);

    private final WebClient webClient;

    private final EndpointProperties idpEndpointProperties;

    public IdpHttpClientImpl(@Qualifier("idpDesWebClient") final WebClient webClient,
            @Qualifier("idpEndpointProperties") final EndpointProperties idpEndpointProperties) {
        this.webClient = webClient;
        this.idpEndpointProperties = idpEndpointProperties;
    }

    @Override
    public Mono<HttpStatus> notifyWorkerTrack(final FleetTelemetryIdpOrderDTO fleetTelemetryIdpOrderDTO) {
        return webClient.post()
                .uri(idpEndpointProperties.getPath())
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(fleetTelemetryIdpOrderDTO)
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        LOGGER.error("Error while notifying IDP: {}.", clientResponse.statusCode());
                        return Mono.error(new FleetTelemetryIdpException(clientResponse.statusCode().getReasonPhrase()));
                    }
                    return Mono.just(clientResponse.statusCode());
                })
                .timeout(ofMillis(idpEndpointProperties.getTimeoutMillis()))
                .doOnError(throwable -> LOGGER.error("An error occurred while notifying the following track to IDP: {}",
                    fleetTelemetryIdpOrderDTO,
                    throwable))
                .doOnSuccess(tuple -> LOGGER.debug("Track was sent to IDP: {}", fleetTelemetryIdpOrderDTO));
    }

}
