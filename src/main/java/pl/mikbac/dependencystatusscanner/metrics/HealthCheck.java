package pl.mikbac.dependencystatusscanner.metrics;

import org.springframework.boot.health.contributor.Status;

/**
 * Created by MikBac on 02.11.2025
 */

public interface HealthCheck {

    String getHealthCheckName();

    Status getHealthCheckStatus();

}
