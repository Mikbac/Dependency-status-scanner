package pl.mikbac.dependencystatusscanner.provider;

import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

/**
 * Created by MikBac on 14.04.2024
 */
public interface ProjectDataProvider {

    ProjectStatusRecordModel getProjectUpdateRecord(final ProjectModel project);

}
