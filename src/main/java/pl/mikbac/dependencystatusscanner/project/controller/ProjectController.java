package pl.mikbac.dependencystatusscanner.project.controller;

import io.swagger.v3.oas.annotations.Operation;
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

@RestController("/projects/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectFacade projectService;

    @GetMapping()
    @Operation(summary = "Get projects with dependencies.", description = "Get a list of paginated projects with dependencies.")
    public ResponseEntity<ResponsePageData<ProjectData>> getProjects(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                     @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getAllProjects(pageNumber, pageSize));
    }

}
