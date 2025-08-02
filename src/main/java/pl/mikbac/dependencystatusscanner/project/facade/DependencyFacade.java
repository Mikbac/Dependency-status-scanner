package pl.mikbac.dependencystatusscanner.project.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mikbac.dependencystatusscanner.project.converter.DependencyConverter;
import pl.mikbac.dependencystatusscanner.project.converter.PageConverter;
import pl.mikbac.dependencystatusscanner.project.data.DependencyRequestData;
import pl.mikbac.dependencystatusscanner.project.data.DependencyResponseData;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;
import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by MikBac on 03.05.2025
 */

@Service
@RequiredArgsConstructor
public class DependencyFacade {

    private final ProjectService projectService;

    public ResponsePageData<DependencyResponseData> getAllDependencies(final int pageNumber, final int pageSize) {
        final PageModel<DependencyModel> dependencyPage = projectService.findAllDependencies(pageNumber, pageSize);
        final List<DependencyResponseData> dependencyData = dependencyPage
                .elements()
                .stream()
                .map(DependencyConverter::toDependencyData)
                .toList();

        return PageConverter.toResponsePageData(dependencyData, pageNumber, pageSize, dependencyPage.totalElements());
    }

    public DependencyResponseData getDependencyByCode(final String dependencyCode) {
        return projectService.findDependencyByCode(dependencyCode)
                .map(DependencyConverter::toDependencyData)
                .orElseThrow(() -> new NoSuchElementException("Dependency with code " + dependencyCode + " not found!"));
    }

    public void addDependency(final DependencyRequestData dependencyData) {
        projectService.findProjectByCode(dependencyData.code()).ifPresent(p -> {
            throw new IllegalArgumentException("Dependency with code " + p + " already exists!");
        });
        final DependencyModel dependencyModel = DependencyConverter.toDependencyModel(dependencyData);
        projectService.addNewProjectDependency(dependencyModel);
    }

}
