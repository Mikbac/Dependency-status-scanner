package pl.mikbac.dependencystatusscanner.project.data;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Created by MikBac on 03.05.2024
 */

@Builder
public record ProjectData(String code,
                          String name,
                          ProjectStatusData projectStatus,
                          LocalDateTime lastUpdate) {
}
