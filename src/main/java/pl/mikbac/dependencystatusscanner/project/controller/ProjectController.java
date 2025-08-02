package pl.mikbac.dependencystatusscanner.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mikbac.dependencystatusscanner.project.data.ProjectRequestData;
import pl.mikbac.dependencystatusscanner.project.data.ProjectResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.facade.ProjectFacade;

/**
 * Created by MikBac on 03.05.2024
 */

@RestController
@RequestMapping("/projects/v1")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectFacade projectService;

    @GetMapping()
    @Operation(summary = "Get projects with dependencies.", description = "Get a list of paginated projects with dependencies.")
    public ResponseEntity<ResponsePageData<ProjectResponseData>> getProjects(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                             @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getAllProjects(pageNumber, pageSize));
    }

    @GetMapping("/{projectCode}")
    @Operation(summary = "Get the project with dependencies based on the code.", description = "Get the specified project with dependencies based on the project code.")
    public ResponseEntity<ProjectResponseData> getProjectByCode(@PathVariable final String projectCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getProjectByCode(projectCode));
    }

    @PostMapping
    @Operation(summary = "Add a new project.", description = "Insert a new project.")
    public ResponseEntity<Void> addProject(final @Valid @RequestBody ProjectRequestData project) {
        projectService.addProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
