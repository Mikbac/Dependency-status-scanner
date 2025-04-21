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

    public static ProjectData toProjectData(final ProjectModel project) {
        final ProjectStatusData projectStatus = toLastStatusRecord(project.projectStatusRecords())
                .map(ProjectConverter::toProjectStatusData)
                .orElse(null);
        return ProjectData.builder()
                .code(project.projectCode())
                .name(project.name())
                .projectStatus(projectStatus)
                .lastUpdate(toLastUpdateDate(project.updatedAt()))
                .build();
    }

    private static ProjectStatusData toProjectStatusData(final ProjectStatusRecordModel projectStatusRecord) {
        return ProjectStatusData.builder()
                .openIssues(projectStatusRecord.openIssues())
                .build();
    }

    private static Optional<ProjectStatusRecordModel> toLastStatusRecord(final Set<ProjectStatusRecordModel> statusRecords) {
        return statusRecords.stream()
                .max(Comparator.comparing(AbstractModel::getCreatedAt));
    }

    private static LocalDateTime toLastUpdateDate(final Timestamp scannerUpdatedAt) {
        return Optional.ofNullable(scannerUpdatedAt)
                .map(Timestamp::toLocalDateTime)
                .orElse(null);
    }

}
