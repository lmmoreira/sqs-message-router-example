package com.fleet.telemetry.idp.model;

import java.time.ZonedDateTime;

/**
 * DTO for sending telemetry information to IDP.
 */
public class FleetTelemetryIdpOrderDTO {

    private final Long orderId;

    private final String orderExternalId;

    private final ZonedDateTime originalEventDate;

    private final ZonedDateTime processedEventDate;

    private final Double latitude;

    private final Double longitude;

    private final Long eta;

    private final String workerName;

    private final String workerPhone;

    public FleetTelemetryIdpOrderDTO(final Long orderId, final String orderExternalId, final long eta,
            final FleetTelemetryDriverDTO telemetryDriveDTO) {
        this.orderId = orderId;
        this.orderExternalId = orderExternalId;
        this.originalEventDate = telemetryDriveDTO.getTrackDate();
        this.processedEventDate = telemetryDriveDTO.getEventDate();
        this.latitude = telemetryDriveDTO.getLatitude();
        this.longitude = telemetryDriveDTO.getLongitude();
        this.eta = eta;
        this.workerName = telemetryDriveDTO.getWorkerName();
        this.workerPhone = telemetryDriveDTO.getWorkerPhone();
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderExternalId() {
        return orderExternalId;
    }

    public ZonedDateTime getOriginalEventDate() {
        return originalEventDate;
    }

    public ZonedDateTime getProcessedEventDate() {
        return processedEventDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getEta() {
        return eta;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    @Override
    public String toString() {
        return "FleetTelemetryIdpOrderDTO{" +
            "orderId=" +
            orderId +
            ", orderExternalId='" +
            orderExternalId +
            '\'' +
            ", originalEventDate=" +
            originalEventDate +
            ", processedEventDate=" +
            processedEventDate +
            ", latitude=" +
            latitude +
            ", longitude=" +
            longitude +
            ", eta=" +
            eta +
            ", workerName='" +
            workerName +
            '\'' +
            ", workerPhone='" +
            workerPhone +
            '\'' +
            '}';
    }
}
