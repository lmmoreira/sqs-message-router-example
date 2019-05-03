package com.fleet.telemetry.idp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * DTO used to handle telemetry information sent by driver.
 */
public class FleetTelemetryDriverDTO {

    private static final double METER_PER_SECOND_TO_KM_PER_HOUR_FACTOR = 3.6;

    public static class OrderIdDTO {

        private final Long orderId;

        private final String externalId;

        @JsonCreator
        public OrderIdDTO(@JsonProperty(value = "orderId", required = true) final Long orderId,
                @JsonProperty(value = "externalId", required = true) final String externalId) {
            this.orderId = orderId;
            this.externalId = externalId;
        }

        public Long getOrderId() {
            return orderId;
        }

        public String getExternalId() {
            return externalId;
        }

        @Override
        public String toString() {
            return "OrderIdDTO{" + "orderId=" + orderId + ", externalId='" + externalId + '\'' + '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final OrderIdDTO that = (OrderIdDTO) o;
            return orderId.equals(that.orderId) && externalId.equals(that.externalId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, externalId);
        }
    }

    private final Double latitude;

    private final Double longitude;

    private final ZonedDateTime trackDate;

    private final ZonedDateTime eventDate;

    private final Set<OrderIdDTO> currentOrders;

    private final Double serviceLatitude;

    private final Double serviceLongitude;

    private final Double legCorrectionFactor;

    private final Double speed;

    private final String workerName;

    private final String workerPhone;

    @JsonCreator
    public FleetTelemetryDriverDTO(@JsonProperty(value = "latitude", required = true) final Double latitude,
            @JsonProperty(value = "longitude", required = true) final Double longitude,
            @JsonProperty(value = "trackDate", required = true) final ZonedDateTime trackDate,
            @JsonProperty(value = "eventDate", required = true) final ZonedDateTime eventDate,
            @JsonProperty(value = "currentOrders", required = true) final Set<OrderIdDTO> currentOrders,
            @JsonProperty(value = "serviceLatitude", required = true) final Double serviceLatitude,
            @JsonProperty(value = "serviceLongitude", required = true) final Double serviceLongitude,
            @JsonProperty(value = "legCorrectionFactor", required = true) final Double legCorrectionFactor,
            @JsonProperty(value = "speed", required = true) final Double speed,
            @JsonProperty("workerName") final String workerName,
            @JsonProperty("workerPhone") final String workerPhone) {
        Assert.notEmpty(currentOrders, "Current orders can't be empty.");
        this.latitude = latitude;
        this.longitude = longitude;
        this.trackDate = trackDate;
        this.eventDate = eventDate;
        this.currentOrders = currentOrders;
        this.serviceLatitude = serviceLatitude;
        this.serviceLongitude = serviceLongitude;
        this.legCorrectionFactor = legCorrectionFactor;
        this.speed = speed * METER_PER_SECOND_TO_KM_PER_HOUR_FACTOR;
        this.workerName = workerName;
        this.workerPhone = workerPhone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ZonedDateTime getTrackDate() {
        return trackDate;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public Set<OrderIdDTO> getCurrentOrders() {
        return currentOrders;
    }

    public Double getServiceLatitude() {
        return serviceLatitude;
    }

    public Double getServiceLongitude() {
        return serviceLongitude;
    }

    public Double getLegCorrectionFactor() {
        return legCorrectionFactor;
    }

    public Double getSpeed() {
        return speed;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    @Override
    public String toString() {
        return "FleetTelemetryDriverDTO{" +
            "latitude=" +
            latitude +
            ", longitude=" +
            longitude +
            ", trackDate=" +
            trackDate +
            ", eventDate=" +
            eventDate +
            ", currentOrders=" +
            currentOrders +
            ", serviceLatitude=" +
            serviceLatitude +
            ", serviceLongitude=" +
            serviceLongitude +
            ", legCorrectionFactor=" +
            legCorrectionFactor +
            ", speed=" +
            speed +
            ", workerName='" +
            workerName +
            '\'' +
            ", workerPhone='" +
            workerPhone +
            '\'' +
            '}';
    }
}
