package com.fleet.telemetry.idp.factory;

import com.fleet.telemetry.idp.builder.FleetTelemetryDriverDTOBuilder;
import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import com.fleet.telemetry.idp.model.FleetTelemetryIdpOrderDTO;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;

@Component
public class FleetTelemetryDTOFactory {

    private final Faker faker;

    public FleetTelemetryDTOFactory(final Faker faker) {
        this.faker = faker;
    }

    private FleetTelemetryDriverDTO.OrderIdDTO getOrderIdDTO() {
        return new FleetTelemetryDriverDTO.OrderIdDTO(2054066L, "2054066");
    }

    public FleetTelemetryDriverDTOBuilder defaultBuilder() {
        return new FleetTelemetryDriverDTOBuilder().withLatitude(-23.23754159733653D)
                .withLongitude(-45.90458773076534D)
                .withTrackDate(ZonedDateTime.now())
                .withEventDate(ZonedDateTime.now().plusSeconds(1L))
                .withOrder(2054066L, "2054066")
                .withServiceLatitude(-23.20426750183105D)
                .withServiceLongitude(-45.85906982421875D)
                .withLegCorrectionFactor(1.5864031151428366D)
                .withSpeed(0.71999997D)
                .withWorkerName(faker.dragonBall().character())
                .withWorkerPhone(faker.phoneNumber().cellPhone());
    }

    public FleetTelemetryDriverDTO getDriverDTO() {
        return defaultBuilder().build();
    }

    public FleetTelemetryIdpOrderDTO getIdpOrderDto() {
        final FleetTelemetryDriverDTO driverDTO = getDriverDTO();
        final FleetTelemetryDriverDTO.OrderIdDTO orderIdDTO =
            driverDTO.getCurrentOrders().stream().findFirst().orElse(getOrderIdDTO());
        return new FleetTelemetryIdpOrderDTO(orderIdDTO.getOrderId(), orderIdDTO.getExternalId(), 538L, driverDTO);
    }
}
