package pl.mikbac.dependencystatusscanner.metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by MikBac on 02.11.2025
 */

@Component
@RequiredArgsConstructor
public class ServicesHealthIndicator implements HealthIndicator {

    private final Map<String, HealthCheck> healthCheckProviders;

    @Override
    public Health health() {
        final Map<String, Status> healthStatuses = getHealthChecks();
        if (healthStatuses.containsValue(Status.DOWN)) {
            return Health.down()
                    .withDetails(healthStatuses)
                    .build();
        } else {
            return Health.up()
                    .withDetails(healthStatuses)
                    .build();
        }
    }

    private Map<String, Status> getHealthChecks() {
        return healthCheckProviders.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getValue().getHealthCheckName(),
                        e -> e.getValue().getHealthCheckStatus()
                ));
    }

}
