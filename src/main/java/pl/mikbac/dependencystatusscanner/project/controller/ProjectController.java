package pl.mikbac.dependencystatusscanner.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.dependencystatusscanner.project.data.ProjectData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.facade.ProjectFacade;

/**
 * Created by MikBac on 03.05.2024
 */

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectFacade projectService;

    @GetMapping("/v1/projects")
    public ResponseEntity<ResponsePageData<ProjectData>> getProjects(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                     @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getAllProjects(pageNumber, pageSize));
    }

}
