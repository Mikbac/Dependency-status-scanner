package pl.mikbac.dependencystatusscanner.scanner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.facade.ProjectFacade;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

/**
 * Created by MikBac on 14.04.2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ScannerService {

    private final ProjectFacade projectFacade;
    private final ProjectsProperties projectsProperties;

    @Scheduled(cron = "${projects.scanner.cron:-}")
    public void scheduleProjectsUpdate() {
        LOGGER.info("Started projects update.");
        projectFacade.getProjectsByOldestEntry(projectsProperties.scanner().batchSize())
                .forEach(this::updateProject);
        LOGGER.info("Ended projects update.");
    }

    private void updateProject(final ProjectModel project) {
        try {
            projectFacade.updateProject(project);
        } catch (Exception e) {
            LOGGER.error("Error updating project [code={}]", project.projectCode(), e);
        }
    }

}
