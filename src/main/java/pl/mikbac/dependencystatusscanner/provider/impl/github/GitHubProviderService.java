package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.provider.ProjectDataProvider;

import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubProviderConfiguration.GITHUB_PROVIDER;

/**
 * Created by MikBac on 03.05.2024
 */

@Service(GITHUB_PROVIDER)
@ConditionalOnBean(GitHubProviderConfiguration.class)
@RequiredArgsConstructor
public class GitHubProviderService implements ProjectDataProvider {

    private final GitHubClient gitHubClient;

    @Override
    @SneakyThrows
    public ProjectStatusRecordModel getProjectUpdateRecord(final ProjectModel project) {
        final GitHubRepositoryModel repository = gitHubClient.getProjectDetails(project);
        return ProjectDetailsConverter.convert(project, repository);
    }
}
