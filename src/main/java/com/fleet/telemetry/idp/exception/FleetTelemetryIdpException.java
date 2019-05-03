package com.fleet.telemetry.idp.exception;

/**
 * General Fleet Telemetry IDP service exception.
 */
public class FleetTelemetryIdpException extends RuntimeException {

    public FleetTelemetryIdpException(final String message) {
        super(message);
    }

    public FleetTelemetryIdpException(final String message, final Exception cause) {
        super(message, cause);
    }

}
