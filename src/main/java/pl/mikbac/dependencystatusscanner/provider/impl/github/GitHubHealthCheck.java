package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.metrics.HealthCheck;

import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubClientConfiguration.GITHUB_CLIENT;

/**
 * Created by MikBac on 03.05.2024
 */

@Slf4j
@Service
@ConditionalOnBean(name = GITHUB_CLIENT)
@RequiredArgsConstructor
public class GitHubHealthCheck implements HealthCheck {

    private final GitHubClient gitHubClient;

    @Override
    public String getHealthCheckName() {
        return "GitHubConnection";
    }

    @Override
    public Status getHealthCheckStatus() {
        return gitHubClient.getProjectResourcesStatus()
                .filter(HttpStatusCode::is2xxSuccessful)
                .map(rs -> Status.UP)
                .orElse(Status.DOWN);
    }
}
