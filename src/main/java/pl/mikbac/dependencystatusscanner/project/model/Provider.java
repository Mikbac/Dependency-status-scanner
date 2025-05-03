package pl.mikbac.dependencystatusscanner.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by MikBac on 03.05.2025
 */

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public enum Provider {
    GITHUB_PROVIDER("githubProvider");

    private final String serviceId;
}
