package pl.mikbac.dependencystatusscanner.project.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.mikbac.dependencystatusscanner.project.data.DependencyRequestData;
import pl.mikbac.dependencystatusscanner.project.data.DependencyResponseData;
import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;

/**
 * Created by MikBac on 03.05.2025
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyConverter {

    public static DependencyResponseData toDependencyData(final DependencyModel dependency) {
        return DependencyResponseData.builder()
                .code(dependency.code())
                .group(dependency.depGroup())
                .artifact(dependency.depArtifact())
                .projectCode(dependency.project().projectCode())
                .build();
    }

    public static DependencyModel toDependencyModel(final DependencyRequestData dependencyData) {
        return new DependencyModel()
                .code(dependencyData.code())
                .depGroup(dependencyData.group())
                .depArtifact(dependencyData.artifact())
                .project(new ProjectModel().projectCode(dependencyData.projectCode()));
    }

}
