package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;

import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubProviderConfiguration.GITHUB_CLIENT;

/**
 * Created by MikBac on 03.05.2024
 */

@Service(GITHUB_CLIENT)
@ConditionalOnBean(GitHubProviderConfiguration.class)
@RequiredArgsConstructor
public class GitHubClient {

    private static final String REPOSITORY_PATH_FORMAT = "/repos/%s/%s";

    private final RestTemplate restTemplate;

    GitHubRepositoryModel getProjectDetails(final ProjectModel project) {
        final String repositoryPath = String.format(REPOSITORY_PATH_FORMAT, project.getProjectExternalId1(), project.getProjectExternalId2());
        return restTemplate.getForObject(repositoryPath, GitHubRepositoryModel.class);
    }

}
