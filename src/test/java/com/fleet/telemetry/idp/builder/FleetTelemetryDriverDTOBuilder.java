package com.fleet.telemetry.idp.builder;

import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class FleetTelemetryDriverDTOBuilder {

    private Double latitude;
    private Double longitude;
    private ZonedDateTime trackDate;
    private ZonedDateTime eventDate;
    private final Set<FleetTelemetryDriverDTO.OrderIdDTO> currentOrders = new HashSet<>();
    private Double serviceLatitude;
    private Double serviceLongitude;
    private Double legCorrectionFactor;
    private Double speed;
    private String workerName;
    private String workerPhone;

    public FleetTelemetryDriverDTOBuilder withLatitude(final Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withLongitude(final Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withTrackDate(final ZonedDateTime trackDate) {
        this.trackDate = trackDate;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withEventDate(final ZonedDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withOrder(final Long orderId, final String externalId) {
        this.currentOrders.add(new FleetTelemetryDriverDTO.OrderIdDTO(orderId, externalId));
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withServiceLatitude(final Double serviceLatitude) {
        this.serviceLatitude = serviceLatitude;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withServiceLongitude(final Double serviceLongitude) {
        this.serviceLongitude = serviceLongitude;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withLegCorrectionFactor(final Double legCorrectionFactor) {
        this.legCorrectionFactor = legCorrectionFactor;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withSpeed(final Double speed) {
        this.speed = speed;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withWorkerName(final String workerName) {
        this.workerName = workerName;
        return this;
    }

    public FleetTelemetryDriverDTOBuilder withWorkerPhone(final String workerPhone) {
        this.workerPhone = workerPhone;
        return this;
    }

    public FleetTelemetryDriverDTO build() {
        return new FleetTelemetryDriverDTO(latitude, longitude, trackDate, eventDate, currentOrders, serviceLatitude,
            serviceLongitude, legCorrectionFactor, speed, workerName, workerPhone);
    }
}
