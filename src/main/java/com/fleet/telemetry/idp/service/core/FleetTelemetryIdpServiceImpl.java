package com.fleet.telemetry.idp.service.core;

import com.fleet.telemetry.idp.model.FleetTelemetryDriverDTO;
import com.fleet.telemetry.idp.model.FleetTelemetryIdpOrderDTO;
import com.fleet.telemetry.idp.service.EtaService;
import com.fleet.telemetry.idp.service.FleetTelemetryIdpService;
import com.fleet.telemetry.idp.service.IdpHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class FleetTelemetryIdpServiceImpl implements FleetTelemetryIdpService {

    private static final String COMPLEMENT_ORDER_FLAG_CHAR = "#";

    @Autowired
    private EtaService etaService;

    @Autowired
    private IdpHttpClient idpHttpClient;

    @Override
    public Flux<HttpStatus> send(final FleetTelemetryDriverDTO telemetryDriveDTO) {
        final long eta = etaService.calculateEta(telemetryDriveDTO);

        return Flux.fromIterable(telemetryDriveDTO.getCurrentOrders())
                .filter(this::isNotComplementOrder)
                .map(currentOrder -> new FleetTelemetryIdpOrderDTO(currentOrder.getOrderId(),
                    currentOrder.getExternalId(), eta, telemetryDriveDTO))
                .flatMap(idpHttpClient::notifyWorkerTrack);
    }

    private boolean isNotComplementOrder(final FleetTelemetryDriverDTO.OrderIdDTO orderIdDTO) {
        return isNotBlank(orderIdDTO.getExternalId()) &&
            !orderIdDTO.getExternalId().contains(COMPLEMENT_ORDER_FLAG_CHAR);
    }

}
