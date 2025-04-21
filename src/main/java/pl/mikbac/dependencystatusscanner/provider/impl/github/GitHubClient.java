package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GitHubClient {

    private static final String REPOSITORY_PATH_FORMAT = "/repos/{owner}/{repo}";

    private final RestClient restClient;

    GitHubRepositoryModel getProjectDetails(final ProjectModel project) {
        return restClient.get().uri(REPOSITORY_PATH_FORMAT, project.projectExternalId1(), project.projectExternalId2()).retrieve().toEntity(GitHubRepositoryModel.class).getBody();
    }

}
