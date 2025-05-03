package pl.mikbac.dependencystatusscanner.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.project.repository.DependencyRepository;
import pl.mikbac.dependencystatusscanner.project.repository.ProjectRepository;
import pl.mikbac.dependencystatusscanner.project.repository.ProjectStatusRepository;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by MikBac on 19.09.2024
 */

@RequiredArgsConstructor
@Service
public class ProjectServiceBasicImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final DependencyRepository dependencyRepository;
    private final ProjectStatusRepository projectStatusRepository;

    @Override
    public PageModel<ProjectModel> findAllProjects(final int pageNumber, final int pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final Page<ProjectModel> projects = projectRepository.findAll(pageable);
        return PageModel.<ProjectModel>builder()
                .elements(projects.toList())
                .totalElements(projects.getTotalElements())
                .build();
    }

    @Override
    public Optional<ProjectModel> findProjectByCode(final String code) {
        return projectRepository.findFirstByProjectCode(code);
    }

    @Override
    public List<ProjectModel> findProjectsByOldestUpdateAt(final int batchSize) {
        final Pageable pageable = PageRequest.of(0, batchSize, Sort.Direction.ASC, "updatedAt");
        return projectRepository.findAll(pageable).stream().toList();
    }

    @Override
    public void addNewProject(final ProjectModel project) {
        projectRepository.save(project);
    }

    @Override
    public PageModel<DependencyModel> findAllDependencies(final int pageNumber, final int pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final Page<DependencyModel> dependencies = dependencyRepository.findAll(pageable);
        return PageModel.<DependencyModel>builder()
                .elements(dependencies.toList())
                .totalElements(dependencies.getTotalElements())
                .build();
    }

    @Override
    public Optional<DependencyModel> findDependencyByCode(final String code) {
        return dependencyRepository.findByCode(code);
    }

    @Override
    public void addNewProjectDependency(final DependencyModel dependency) {
        final Optional<ProjectModel> project = projectRepository.findFirstByProjectCode(dependency.project().projectCode());
        project.map(p -> dependencyRepository.save(dependency.project(p)))
                .orElseThrow(() -> new NoSuchElementException("Project not found"));
    }

    @Override
    @Transactional
    public void addNewProjectStatusRecord(final ProjectStatusRecordModel projectStatusRecord) {
        projectStatusRepository.save(projectStatusRecord);
        projectRepository.setScannerUpdate(new Timestamp(System.currentTimeMillis()),
                projectStatusRecord.project().getId());
    }
}
