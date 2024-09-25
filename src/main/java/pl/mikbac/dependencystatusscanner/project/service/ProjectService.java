package pl.mikbac.dependencystatusscanner.project.service;

import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

import java.util.List;

/**
 * Created by MikBac on 03.05.2024
 */

public interface ProjectService {

    PageModel<ProjectModel> findAllProjects(int pageNumber, int pageSize);

    List<ProjectModel> findProjectsByOldestEntry(int batchSize);

    void saveProjectStatusRecord(ProjectStatusRecordModel projectStatusRecord);

}
