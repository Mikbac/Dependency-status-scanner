package pl.mikbac.dependencystatusscanner.provider.impl.github;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;

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
    GitHubRepositoryModel getProjectDetails(final ProjectModel project) {
        return restClient.get().uri(REPOSITORY_PATH_FORMAT, project.projectExternalId1(), project.projectExternalId2()).retrieve().toEntity(GitHubRepositoryModel.class).getBody();
    }

    GitHubRepositoryModel getProjectDetailsFallback(final ProjectModel project, Throwable error) {
        LOGGER.error("GitHub API is unavailable, fetching new data is impossible!");
        LOGGER.error("Error from GitHub API: " + error.getMessage());
        return null;
    }

}
