package pl.mikbac.dependencystatusscanner.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Created by MikBac on 11.08.2024
 */

@ConfigurationProperties("projects")
@Validated
public record ProjectsProperties(ScannerProperties scanner,
                                 ProviderProperties provider) {
    public record ScannerProperties(
            @NotNull(message = "Connection timeout should not be null.")
            @PositiveOrZero(message = "Batch size should be greater than or equal to 0.") int batchSize,
            AsyncExecutorProperties asyncExecutor) {
    }

    public record ProviderProperties(GithubProperties github) {
        public record GithubProperties(
                @NotBlank(message = "Github API URL should not be empty.") @URL(message = "Github API URL should have the correct format.") String url,
                String token,
                @PositiveOrZero(message = "Connection timeout should be greater than or equal to 0.") int connectionTimeout,
                @PositiveOrZero(message = "Read timeout should be greater than or equal to 0.") int readTimeout) {
        }
    }

    public record AsyncExecutorProperties(
            @PositiveOrZero(message = "Core pool size should be greater than or equal to 0.") int corePoolSize,
            @PositiveOrZero(message = "Max pool size should be greater than or equal to 0.") int maxPoolSize,
            @PositiveOrZero(message = "Queue capacity should be greater than or equal to 0.") int queueCapacity) {

    }
}
