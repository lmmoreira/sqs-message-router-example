package com.fleet.telemetry.idp.service;

import com.fleet.telemetry.idp.model.FleetTelemetryIdpOrderDTO;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * HTTP client for sending telemetry information to IDP.
 */
public interface IdpHttpClient {

    /**
     * Notify worker track to IDP service via HTTP request.
     * @param fleetTelemetryIdpOrderDTO DTO containing telemetry information.
     * @return {@link Mono} with HTTP response status.
     */
    Mono<HttpStatus> notifyWorkerTrack(FleetTelemetryIdpOrderDTO fleetTelemetryIdpOrderDTO);

}
