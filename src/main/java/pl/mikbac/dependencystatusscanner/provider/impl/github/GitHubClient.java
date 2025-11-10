package pl.mikbac.dependencystatusscanner.provider.impl.github;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;

import java.util.Optional;

import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubClientConfiguration.GITHUB_CLIENT;
import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubClientConfiguration.GITHUB_PROVIDER_REST_CLIENT;

/**
 * Created by MikBac on 03.05.2024
 */

@Service(GITHUB_CLIENT)
@ConditionalOnBean(name = GITHUB_PROVIDER_REST_CLIENT)
@Slf4j
@RequiredArgsConstructor
public class GitHubClient {

    private static final String REPOSITORY_PATH_FORMAT = "/repos/{owner}/{repo}";

    private final RestClient restClient;

    @CircuitBreaker(name = "GitHubRepositoryDetails", fallbackMethod = "getProjectDetailsFallback")
    Optional<GitHubRepositoryModel> getProjectDetails(final ProjectModel project) {
        return Optional.ofNullable(restClient.get()
                .uri(REPOSITORY_PATH_FORMAT, project.projectExternalId1(), project.projectExternalId2())
                .retrieve()
                .toEntity(GitHubRepositoryModel.class)
                .getBody());
    }

    Optional<GitHubRepositoryModel> getProjectDetailsFallback(final ProjectModel project, Throwable error) {
        LOGGER.error("GitHub API is unavailable, fetching new data is impossible!");
        LOGGER.error("Error from GitHub API: {}", error.getMessage());
        return Optional.empty();
    }

    @CircuitBreaker(name = "GitHubResourcesStatus", fallbackMethod = "getResourcesStatusFallback")
    Optional<HttpStatusCode> getProjectResourcesStatus() {
        return Optional.of(restClient.get()
                .retrieve()
                .toBodilessEntity()
                .getStatusCode());
    }

    Optional<HttpStatusCode> getResourcesStatusFallback(Throwable error) {
        LOGGER.error("GitHub API is unavailable, fetching status is impossible!");
        LOGGER.error("Error from GitHub API: {}", error.getMessage());
        return Optional.empty();
    }

}
