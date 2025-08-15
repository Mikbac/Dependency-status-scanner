package pl.mikbac.dependencystatusscanner.project.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.converter.PageConverter;
import pl.mikbac.dependencystatusscanner.project.converter.ProjectConverter;
import pl.mikbac.dependencystatusscanner.project.data.ProjectRequestData;
import pl.mikbac.dependencystatusscanner.project.data.ProjectResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;
import pl.mikbac.dependencystatusscanner.provider.ProjectDataProvider;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by MikBac on 03.05.2024
 */

@Service
@RequiredArgsConstructor
public class ProjectFacade {

    private final ProjectService projectService;
    private final Map<String, ProjectDataProvider> projectsProviders;

    public ResponsePageData<ProjectResponseData> getAllProjects(final int pageNumber, final int pageSize) {
        final PageModel<ProjectModel> projectPage = projectService.findAllProjects(pageNumber, pageSize);
        final List<ProjectResponseData> projectData = projectPage
                .elements()
                .stream()
                .map(ProjectConverter::toProjectData)
                .toList();

        return PageConverter.toResponsePageData(projectData, pageNumber, pageSize, projectPage.totalElements());
    }

    public ProjectResponseData getProjectByCode(final String projectCode) {
        return projectService.findProjectByCode(projectCode)
                .map(ProjectConverter::toProjectData)
                .orElseThrow(() -> new NoSuchElementException("Project with code " + projectCode + " not found!"));
    }

    public List<ProjectModel> getProjectsByOldestEntry(final int batchSize) {
        return projectService.findProjectsByOldestUpdateAt(batchSize);
    }

    public void addProject(final ProjectRequestData projectData) {
        projectService.findProjectByCode(projectData.code()).ifPresent(p -> {
            throw new IllegalArgumentException("Project with code " + p + " already exists!");
        });
        final ProjectModel projectModel = ProjectConverter.toProjectModel(projectData);
        projectService.addNewProject(projectModel);
    }

    @Async("updateProjectsExecutor")
    public void updateProject(final ProjectModel project) {
        final ProjectDataProvider provider = projectsProviders.get(project.providerCode().serviceId());
        if (Objects.isNull(provider)) {
            throw new IllegalArgumentException("Unsupported or inactive provider: " + project.providerCode());
        }
        provider.getProjectUpdateRecord(project)
                .ifPresentOrElse(projectService::addNewProjectStatusRecord, () -> projectService.markLastProjectUpdateAsFailed(project));
    }
}
