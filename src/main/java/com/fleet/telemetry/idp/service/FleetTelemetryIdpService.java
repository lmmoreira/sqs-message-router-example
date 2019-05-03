package com.fleet.telemetry.idp.service;

import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;

import org.springframework.http.HttpStatus;

import reactor.core.publisher.Flux;

/**
 * Service responsible for notifying IDP of fleet telemetry driver data.
 */
public interface FleetTelemetryIdpService {

    /**
     * It creates a {@link Flux} for sending fleet telemetry driver data to IDP.
     * @param telemetryDriveDTO DTO containing fleet telemetry driver.
     * @return Flux with notification status.
     */
    Flux<HttpStatus> send(FleetTelemetryDriverDTO telemetryDriveDTO);

}
