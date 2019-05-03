package com.fleet.telemetry.idp.service;

import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;

/**
 * Service responsible for apply business logic about Estimated Time of Arrival (ETA) for current orders.
 */
public interface EtaService {

    /**
     * Method where ETA is calculated from an valid {@link FleetTelemetryDriverDTO} instance.
     * @param telemetryDriveDTO Valid instance.
     * @return Estimated Time of Arrival.
     */
    long calculateEta(FleetTelemetryDriverDTO telemetryDriveDTO);

}
