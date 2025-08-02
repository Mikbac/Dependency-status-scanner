package pl.mikbac.dependencystatusscanner.project.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by MikBac on 31.05.2025
 */

@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DependencyFacadeTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private DependencyFacade dependencyFacade;

    @Test
    @DisplayName("Get paginated dependencies")
    void getAllDependencies() {
        // Given
        when(projectService.findAllDependencies(anyInt(), anyInt())).thenReturn(
                new PageModel<>(List.of(), 2)
        );
        // When
        var dependencies = dependencyFacade.getAllDependencies(0, 5);

        // Then
        assertEquals(0, dependencies.pageNumber());
        assertEquals(5, dependencies.pageSize());
        assertEquals(2, dependencies.totalElements());

    }

}
