package pl.mikbac.dependencystatusscanner.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.mikbac.dependencystatusscanner.project.data.DependencyResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.facade.DependencyFacade;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by MikBac on 31.05.2025
 */

@ExtendWith(MockitoExtension.class)
class DependencyControllerTest {

    @Mock
    private DependencyFacade dependencyFacade;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DependencyController(dependencyFacade))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Should return 200 OK with list of dependencies")
    void shouldReturn200WithDependenciesList() throws Exception {
        // give
        when(dependencyFacade.getAllDependencies(anyInt(), anyInt()))
                .thenReturn(new ResponsePageData<>(List.of(new DependencyResponseData("code", "code", "code", "code")), 0, 5, 1));
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/dependencies/v1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                           {
                            data: [
                                {
                                    code: "code",
                                    group: "code",
                                    artifact: "code",
                                    projectCode: "code"
                                }
                            ],
                            pageNumber: 0,
                            pageSize: 5,
                            totalElements: 1
                           }
                        """))
                .andReturn();

    }

}
