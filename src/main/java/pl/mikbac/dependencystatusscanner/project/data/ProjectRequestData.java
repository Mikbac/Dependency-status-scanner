package pl.mikbac.dependencystatusscanner.project.data;

import lombok.Builder;

/**
 * Created by MikBac on 03.05.2024
 */

@Builder
public record ProjectRequestData(String code,
                                 String name,
                                 String providerCode,
                                 String projectExternalId1,
                                 String projectExternalId2,
                                 String projectExternalId3) {
}
