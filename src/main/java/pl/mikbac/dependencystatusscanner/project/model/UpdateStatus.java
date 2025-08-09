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
public enum UpdateStatus {
    SUCCESS("success"),
    FAILURE("failure"),
    NONE("none");

    private final String status;
}
