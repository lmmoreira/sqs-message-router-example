package com.fleet.telemetry.idp.model.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.telemetry.idp.exception.FleetTelemetryIdpException;
import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FleetTelemetryDriverDTOConverter implements Converter<String, FleetTelemetryDriverDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FleetTelemetryDriverDTOConverter.class);

    private final ObjectMapper objectMapper;

    public FleetTelemetryDriverDTOConverter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public FleetTelemetryDriverDTO convert(@NonNull final String source) {
        try {
            return objectMapper.readValue(source, FleetTelemetryDriverDTO.class);
        } catch (final IOException e) {
            final String errorMessage = "Cannot convert message payload to FleetTelemetryDriverDTO.";
            LOGGER.error(errorMessage, e);
            throw new FleetTelemetryIdpException(errorMessage, e);
        }
    }
}
