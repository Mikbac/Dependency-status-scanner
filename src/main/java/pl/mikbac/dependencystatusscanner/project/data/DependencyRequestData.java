package pl.mikbac.dependencystatusscanner.project.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Created by MikBac on 03.05.2025
 */

@Builder
public record DependencyRequestData(@NotBlank(message = "Dependency code should not be empty.") String code,
                                    @NotBlank(message = "Dependency group should not be empty.") String group,
                                    @NotBlank(message = "Dependency artifact should not be empty.") String artifact,
                                    @NotBlank(message = "Dependency project code should not be empty.") String projectCode) {
}
