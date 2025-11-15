package pl.mikbac.dependencystatusscanner.provider.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.provider.ProjectDataProvider;

import java.util.Optional;

import static pl.mikbac.dependencystatusscanner.provider.github.GitHubClientAutoConfiguration.GITHUB_PROVIDER;

/**
 * Created by MikBac on 03.05.2024
 */

@Slf4j
@Service(GITHUB_PROVIDER)
@ConditionalOnProperty(prefix = "projects.provider.github", name = "active")
@RequiredArgsConstructor
public class GitHubDataProviderService implements ProjectDataProvider {

    private final GitHubClient gitHubClient;

    @Override
    public Optional<ProjectStatusRecordModel> getProjectUpdateRecord(final ProjectModel project) {
        LOGGER.info("Fetching project data [projectCode={}]", project.projectCode());
        LOGGER.debug("Fetching project data [projectCode={}, projectExternalId1={}, projectExternalId2={}, projectExternalId3={}]", project.projectCode(), project.projectExternalId1(), project.projectExternalId2(), project.projectExternalId3());
        final Optional<GitHubRepositoryModel> repository = gitHubClient.getProjectDetails(project);
        return repository.map(repositoryModel -> ProjectDetailsConverter.convert(project, repositoryModel));
    }
}
