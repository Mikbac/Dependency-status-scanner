package pl.mikbac.dependencystatusscanner.project.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.mikbac.dependencystatusscanner.project.data.ProjectData;
import pl.mikbac.dependencystatusscanner.project.data.ProjectStatusData;
import pl.mikbac.dependencystatusscanner.project.model.AbstractModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

/**
 * Created by MikBac on 03.05.2024
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConverter {

    public static ProjectData getProjectData(final ProjectModel project) {
        final ProjectStatusData projectStatus = getLastStatusRecord(project.getProjectStatusRecords())
                .map(ProjectConverter::getProjectStatusData)
                .orElse(null);
        return ProjectData.builder()
                .code(project.getProjectCode())
                .name(project.getName())
                .projectStatus(projectStatus)
                .lastUpdate(getLastUpdateDate(project.getLastSuccessScannerUpdate()))
                .build();
    }

    private static ProjectStatusData getProjectStatusData(final ProjectStatusRecordModel projectStatusRecord) {
        return ProjectStatusData.builder()
                .openIssues(projectStatusRecord.getOpenIssues())
                .build();
    }

    private static Optional<ProjectStatusRecordModel> getLastStatusRecord(final Set<ProjectStatusRecordModel> statusRecords) {
        return statusRecords.stream()
                .max(Comparator.comparing(AbstractModel::getInitData));
    }

    private static LocalDateTime getLastUpdateDate(final Timestamp lastSuccessScannerUpdate) {
        return Optional.ofNullable(lastSuccessScannerUpdate)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }

}
