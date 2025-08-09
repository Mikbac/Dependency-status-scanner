package pl.mikbac.dependencystatusscanner.project.service;

import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

import java.util.List;
import java.util.Optional;

/**
 * Created by MikBac on 03.05.2024
 */

public interface ProjectService {

    PageModel<ProjectModel> findAllProjects(int pageNumber, int pageSize);

    Optional<ProjectModel> findProjectByCode(String code);

    List<ProjectModel> findProjectsByOldestUpdateAt(int batchSize);

    void addNewProject(ProjectModel project);

    PageModel<DependencyModel> findAllDependencies(int pageNumber, int pageSize);

    Optional<DependencyModel> findDependencyByCode(String code);

    void addNewProjectDependency(DependencyModel dependency);

    void addNewProjectStatusRecord(ProjectStatusRecordModel projectStatusRecord);

    void markLastProjectUpdateAsFailed(ProjectModel project);

}
