package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.provider.ProjectDataProvider;

import java.util.Optional;

import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubClientConfiguration.GITHUB_CLIENT;
import static pl.mikbac.dependencystatusscanner.provider.impl.github.GitHubClientConfiguration.GITHUB_PROVIDER;

/**
 * Created by MikBac on 03.05.2024
 */

@Slf4j
@Service(GITHUB_PROVIDER)
@ConditionalOnBean(name = GITHUB_CLIENT)
@RequiredArgsConstructor
public class GitHubDataProviderService implements ProjectDataProvider {

    private final GitHubClient gitHubClient;

    @Override
    @SneakyThrows
    public Optional<ProjectStatusRecordModel> getProjectUpdateRecord(final ProjectModel project) {
        LOGGER.info("Fetching project data [projectCode={}]", project.projectCode());
        LOGGER.debug("Fetching project data [projectCode={}, projectExternalId1={}, projectExternalId2={}, projectExternalId3={}]", project.projectCode(), project.projectExternalId1(), project.projectExternalId2(), project.projectExternalId3());
        final Optional<GitHubRepositoryModel> repository = gitHubClient.getProjectDetails(project);
        return repository.map(r -> ProjectDetailsConverter.convert(project, r));
    }
}
