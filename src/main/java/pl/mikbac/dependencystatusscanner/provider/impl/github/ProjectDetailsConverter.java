package pl.mikbac.dependencystatusscanner.provider.impl.github;

import lombok.experimental.UtilityClass;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

/**
 * Created by MikBac on 19.09.2024
 */

@UtilityClass
public class ProjectDetailsConverter {

    public ProjectStatusRecordModel convert(final ProjectModel project,
                                            final GitHubRepositoryModel repositoryModel) {
        final ProjectStatusRecordModel projectStatusRecord = new ProjectStatusRecordModel();
        projectStatusRecord.setProject(project);
        projectStatusRecord.setOpenIssues(repositoryModel.open_issues());
        return projectStatusRecord;
    }

}
