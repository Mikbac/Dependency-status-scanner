package pl.mikbac.dependencystatusscanner.scanner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.facade.ProjectFacade;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

/**
 * Created by MikBac on 14.04.2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ScannerService {

    private final ProjectFacade projectService;
    private final ProjectsProperties projectsProperties;

    @Scheduled(cron = "${projects.scanner.cron}")
    public void scheduleProjectsUpdate() {
        LOGGER.info("Started project update.");
        projectService.getProjectsByOldestEntry(projectsProperties.scanner().batchSize())
                .forEach(projectService::updateProject);
        LOGGER.info("Ended project update.");
    }

}
