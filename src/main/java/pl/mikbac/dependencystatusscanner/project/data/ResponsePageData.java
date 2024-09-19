package pl.mikbac.dependencystatusscanner.project.data;

import lombok.Builder;

import java.util.List;

/**
 * Created by MikBac on 04.05.2024
 */

@Builder
public record ResponsePageData<T>(List<T> data,
                                  int pageNumber,
                                  int pageSize,
                                  long totalElements) {
}
