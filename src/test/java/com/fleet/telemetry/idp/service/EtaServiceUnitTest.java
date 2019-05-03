package com.fleet.telemetry.idp.service;

import com.fleet.telemetry.idp.BaseTest;
import com.fleet.telemetry.idp.factory.FleetTelemetryDTOFactory;
import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;

public class EtaServiceUnitTest extends BaseTest {

    @Autowired
    private EtaService etaService;

    @Autowired
    private FleetTelemetryDTOFactory fleetTelemetryDTOFactory;

    @Test
    public void testCalculateEta() {
        final FleetTelemetryDriverDTO driverDTO = fleetTelemetryDTOFactory.defaultBuilder()
                .withLatitude(-23.23754159733653D)
                .withLongitude(-45.90458773076534)
                .withServiceLatitude(-23.20426750183105D)
                .withServiceLongitude(-45.85906982421875D)
                .withLegCorrectionFactor(1.5864031151428366D)
                .withSpeed(0.71999997D) // meters per second
                .build();
        assertEquals("ETA should be 13095 when worker is driving at 0.71999997 m/s.",
            etaService.calculateEta(driverDTO),
            13095L);
    }

    @Test
    public void testToleranceAsSamePointWhenCalculateEta() {
        final FleetTelemetryDriverDTO driverDTO = fleetTelemetryDTOFactory.defaultBuilder()
                .withLatitude(-23.237541597D)
                .withLongitude(-45.9045877307D)
                .withServiceLatitude(-23.2375415969D)
                .withServiceLongitude(-45.90458773069D)
                .build();
        assertEquals("ETA should be 0 due tolerance being 0.000000001.",
                etaService.calculateEta(driverDTO), 0L);
    }
}
