package pl.mikbac.dependencystatusscanner.project.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.project.model.Provider;
import pl.mikbac.dependencystatusscanner.project.model.UpdateStatus;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by MikBac on 21.04.2025
 */

@Testcontainers
@SpringBootTest(properties = {
        "spring.flyway.schemas=mock_dependency_scanner",
        "spring.jpa.properties.hibernate.default_schema=mock_dependency_scanner"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectServiceBasicImplIntegrationTest {

    private static final String CLEAN_TABLE_PROJECTS = "TRUNCATE TABLE mock_dependency_scanner.projects RESTART IDENTITY CASCADE";

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.2-alpine");

    @Autowired
    private ProjectService projectService;

    @Test
    @Sql(statements = CLEAN_TABLE_PROJECTS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Should insert and find inserted projects")
    void insertAndFindProjectsTest() {
        // Given
        var project = getTestProjectModel(StringUtils.EMPTY);

        // When
        projectService.addNewProject(project);
        var projects = projectService.findAllProjects(0, 10);

        // Then
        assertThat(projects.elements()).hasSize(1);
        assertThat(projects.totalElements()).isEqualTo(1);
        assertThat(projects.elements().getFirst().projectCode()).isEqualTo("test-project-code");
        assertThat(projects.elements().getFirst().name()).isEqualTo("test-project-name");
        assertThat(projects.elements().getFirst().projectExternalId1()).isEqualTo("test-external-id");
    }

    @Test
    @Sql(statements = CLEAN_TABLE_PROJECTS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Should insert and find inserted project by code")
    void insertAndFindProjectByCodeTest() {
        // Given
        var project = getTestProjectModel(StringUtils.EMPTY);

        // When
        projectService.addNewProject(project);
        var insertedProject = projectService.findProjectByCode("test-project-code").orElseThrow();

        // Then
        assertThat(insertedProject.projectCode()).isEqualTo("test-project-code");
        assertThat(insertedProject.name()).isEqualTo("test-project-name");
        assertThat(insertedProject.providerCode()).isEqualTo(Provider.GITHUB_PROVIDER);
        assertThat(insertedProject.projectExternalId1()).isEqualTo("test-external-id");
    }

    @Test
    @Sql(statements = CLEAN_TABLE_PROJECTS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Should insert and find inserted dependencies")
    void insertAndFindDependenciesTest() {
        // Given
        var project = getTestProjectModel(StringUtils.EMPTY);
        var dependency = getTestDependencyModel();

        // When
        projectService.addNewProject(project);
        projectService.addNewProjectDependency(dependency);
        var dependencies = projectService.findAllDependencies(0, 10);

        // Then
        assertThat(dependencies.elements()).hasSize(1);
        assertThat(dependencies.totalElements()).isEqualTo(1);
        assertThat(dependencies.elements().getFirst().code()).isEqualTo("test-dependency-code");
        assertThat(dependencies.elements().getFirst().depGroup()).isEqualTo("test-dependency-group");
        assertThat(dependencies.elements().getFirst().depArtifact()).isEqualTo("test-dependency-artifact");
    }

    @Test
    @Sql(statements = CLEAN_TABLE_PROJECTS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Should insert and find inserted dependency by code")
    void insertAndFindDependencyByCodeTest() {
        // Given
        var project = getTestProjectModel(StringUtils.EMPTY);
        var dependency = getTestDependencyModel();

        // When
        projectService.addNewProject(project);
        projectService.addNewProjectDependency(dependency);
        var insertedDependency = projectService.findDependencyByCode("test-dependency-code").orElseThrow();

        // Then
        assertThat(insertedDependency.code()).isEqualTo("test-dependency-code");
        assertThat(insertedDependency.depGroup()).isEqualTo("test-dependency-group");
        assertThat(insertedDependency.depArtifact()).isEqualTo("test-dependency-artifact");
    }

    @Test
    @Sql(statements = CLEAN_TABLE_PROJECTS, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Should insert status record and retrieve projects by oldest updateAt")
    void insertStatusRecordAndFindProjectsByOldestUpdateAtTest() {
        // Given
        var projectOne = getTestProjectModel(StringUtils.EMPTY);
        var projectTwo = getTestProjectModel("-2");
        var projectStatusRecord = getTestProjectStatusRecord();

        // When
        projectService.addNewProject(projectOne);
        projectService.addNewProject(projectTwo);
        var projects = projectService.findProjectsByOldestUpdateAt(1);
        projectService.addNewProjectStatusRecord(projectStatusRecord.project(projects.getFirst()));
        var updatedProjects = projectService.findProjectsByOldestUpdateAt(1);

        // Then
        assertThat(updatedProjects).hasSize(1);
        assertThat(updatedProjects.getFirst().projectCode()).isEqualTo("test-project-code-2");
        assertThat(updatedProjects.getFirst().name()).isEqualTo("test-project-name-2");
        assertThat(updatedProjects.getFirst().projectExternalId1()).isEqualTo("test-external-id-2");
    }

    private ProjectModel getTestProjectModel(final String postfix) {
        var project = new ProjectModel();
        project.projectCode("test-project-code" + postfix);
        project.name("test-project-name" + postfix);
        project.providerCode(Provider.GITHUB_PROVIDER);
        project.projectExternalId1("test-external-id" + postfix);
        project.lastUpdateStatus(UpdateStatus.NONE);
        return project;
    }

    private DependencyModel getTestDependencyModel() {
        var dependency = new DependencyModel();
        dependency.code("test-dependency-code");
        dependency.depGroup("test-dependency-group");
        dependency.depArtifact("test-dependency-artifact");
        dependency.project(new ProjectModel().projectCode("test-project-code"));
        return dependency;
    }

    private ProjectStatusRecordModel getTestProjectStatusRecord() {
        var projectStatusRecord = new ProjectStatusRecordModel();
        projectStatusRecord.openIssues(15);
        projectStatusRecord.project(new ProjectModel().projectCode("test-project-code"));
        return projectStatusRecord;
    }
}
