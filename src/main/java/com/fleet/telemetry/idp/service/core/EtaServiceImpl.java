package com.fleet.telemetry.idp.service.core;

import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import com.fleet.telemetry.idp.service.EtaService;
import com.google.common.math.DoubleMath;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
public class EtaServiceImpl implements EtaService {

    private static final double TOLERANCE = 0.000000001D;
    private static final int NUMBER_OF_MINUTES_IN_DEGREE = 60;
    private static final double NUMBER_KM_IN_ONE_MILE = 1.609344D;
    private static final double STATUTE_MILE_TO_NAUTICAL_MILES = 1.1515D;
    private static final int ONE_HOUR_IN_MINUTES = 3600;

    @Override
    public long calculateEta(final FleetTelemetryDriverDTO telemetryDriveDTO) {
        return Math.round(geodesicDistance(telemetryDriveDTO) *
            telemetryDriveDTO.getLegCorrectionFactor() /
            telemetryDriveDTO.getSpeed() *
            ONE_HOUR_IN_MINUTES);
    }

    private double geodesicDistance(final FleetTelemetryDriverDTO telemetryDriveDTO) {
        if (samePoints(telemetryDriveDTO.getLatitude(), telemetryDriveDTO.getServiceLatitude()) &&
            samePoints(telemetryDriveDTO.getLongitude(), telemetryDriveDTO.getServiceLongitude())) {
            return NumberUtils.DOUBLE_ZERO;
        }
        return distanceInDegrees(telemetryDriveDTO.getLatitude(),
            telemetryDriveDTO.getLongitude(),
            telemetryDriveDTO.getServiceLatitude(),
            telemetryDriveDTO.getServiceLongitude());
    }

    private static boolean samePoints(final double firstPoint, final double secondPoint) {
        return DoubleMath.fuzzyEquals(firstPoint, secondPoint, TOLERANCE);
    }

    private double distanceInDegrees(final double latitude, final double longitude, final double serviceLatitude,
                                     final double serviceLongitude) {
        final double theta = longitude - serviceLongitude;
        final double distance = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(serviceLatitude)) +
            Math.cos(Math.toRadians(latitude)) *
                Math.cos(Math.toRadians(serviceLatitude)) *
                Math.cos(Math.toRadians(theta));
        return Math.toDegrees(Math.acos(distance)) *
            NUMBER_OF_MINUTES_IN_DEGREE *
            STATUTE_MILE_TO_NAUTICAL_MILES *
            NUMBER_KM_IN_ONE_MILE;
    }

}
