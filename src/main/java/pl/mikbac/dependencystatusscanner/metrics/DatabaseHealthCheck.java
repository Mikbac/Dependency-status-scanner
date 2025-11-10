package pl.mikbac.dependencystatusscanner.metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by MikBac on 02.11.2025
 */

@Component
@RequiredArgsConstructor
public class DatabaseHealthCheck implements HealthCheck {

    private final DataSource dataSource;

    @Override
    public String getHealthCheckName() {
        return "PostgresConnection";
    }

    @Override
    public Status getHealthCheckStatus() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2) ? Status.UP : Status.DOWN;
        } catch (Exception e) {
            return Status.UNKNOWN;
        }
    }

}
