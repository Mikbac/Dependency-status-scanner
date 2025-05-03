package pl.mikbac.dependencystatusscanner.project.data;

import lombok.Builder;

/**
 * Created by MikBac on 03.05.2025
 */

@Builder
public record DependencyResponseData(String code,
                                     String group,
                                     String artifact,
                                     String projectCode) {
}
