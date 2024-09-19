package pl.mikbac.dependencystatusscanner.project.data;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Created by MikBac on 04.05.2024
 */

@Builder
public record ProjectStatusData(Integer openIssues) {
}
