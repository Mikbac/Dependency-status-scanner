package pl.mikbac.dependencystatusscanner.project.service;

import lombok.Builder;

import java.util.List;

/**
 * Created by MikBac on 19.09.2024
 */

@Builder
public record PageModel<T>(List<T> projects,
                           long totalElements) {
}
