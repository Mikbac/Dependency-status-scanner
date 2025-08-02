package pl.mikbac.dependencystatusscanner.project.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Created by MikBac on 03.05.2024
 */

@Builder
public record ProjectRequestData(@NotBlank(message = "Project code should not be empty.") String code,
                                 @NotBlank(message = "Project name should not be empty.")String name,
                                 @NotBlank(message = "Project provider code should not be empty.")String providerCode,
                                 String projectExternalId1,
                                 String projectExternalId2,
                                 String projectExternalId3) {
}
