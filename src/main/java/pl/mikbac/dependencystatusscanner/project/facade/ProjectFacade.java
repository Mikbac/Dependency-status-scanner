package pl.mikbac.dependencystatusscanner.project.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.converter.PageConverter;
import pl.mikbac.dependencystatusscanner.project.converter.ProjectConverter;
import pl.mikbac.dependencystatusscanner.project.data.ProjectData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;
import pl.mikbac.dependencystatusscanner.provider.ProjectDataProvider;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by MikBac on 03.05.2024
 */

@Service
@RequiredArgsConstructor
public class ProjectFacade {

    private final ProjectService projectService;
    private final Map<String, ProjectDataProvider> projectsProviders;

    public ResponsePageData<ProjectData> getAllProjects(final int pageNumber, final int pageSize) {
        final PageModel<ProjectModel> projectPage = (PageModel<ProjectModel>) projectService.findAllProjects(pageNumber, pageSize);
        final List<ProjectData> projectData = projectPage
                .projects()
                .stream()
                .map(ProjectConverter::getProjectData)
                .toList();

        return PageConverter.getResponsePage(projectData, pageNumber, pageSize, projectPage.totalElements());
    }

    public List<ProjectModel> getProjectsByOldestEntry(final int batchSize) {
        return projectService.findProjectsByOldestEntry(batchSize);
    }

    @Async
    public void updateProject(final ProjectModel project) {
        final ProjectDataProvider provider = projectsProviders.get(project.getProviderId());
        if (Objects.isNull(provider)) {
            throw new IllegalArgumentException("Unsupported or inactive provider: " + project.getProviderId());
        }
        projectService.saveProjectStatusRecord(provider.getProjectUpdateRecord(project));
    }
}
