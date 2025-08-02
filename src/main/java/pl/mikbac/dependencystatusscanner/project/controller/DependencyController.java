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
import pl.mikbac.dependencystatusscanner.project.data.DependencyRequestData;
import pl.mikbac.dependencystatusscanner.project.data.DependencyResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.facade.DependencyFacade;

/**
 * Created by MikBac on 03.05.2024
 */

@RestController
@RequestMapping("/dependencies/v1")
@RequiredArgsConstructor
public class DependencyController {

    private final DependencyFacade dependencyFacade;

    @GetMapping()
    @Operation(summary = "Get dependencies.", description = "Get a list of paginated dependencies.")
    public ResponseEntity<ResponsePageData<DependencyResponseData>> getDependencies(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                                    @RequestParam(defaultValue = "10") final int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(dependencyFacade.getAllDependencies(pageNumber, pageSize));
    }

    @GetMapping("/{dependencyCode}")
    @Operation(summary = "Get the dependency based on the code.", description = "Get the specified dependency based on the dependency code.")
    public ResponseEntity<DependencyResponseData> getDependencyByCode(@PathVariable final String dependencyCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(dependencyFacade.getDependencyByCode(dependencyCode));
    }

    @PostMapping
    @Operation(summary = "Add a new dependency.", description = "Insert a new dependency with the project code.")
    public ResponseEntity<Void> addDependency(final @Valid @RequestBody DependencyRequestData dependency) {
        dependencyFacade.addDependency(dependency);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
