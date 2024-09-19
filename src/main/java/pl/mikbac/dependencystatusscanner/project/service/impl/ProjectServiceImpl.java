package pl.mikbac.dependencystatusscanner.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;
import pl.mikbac.dependencystatusscanner.project.repository.ProjectRepository;
import pl.mikbac.dependencystatusscanner.project.repository.ProjectStatusRepository;
import pl.mikbac.dependencystatusscanner.project.service.PageModel;
import pl.mikbac.dependencystatusscanner.project.service.ProjectService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by MikBac on 19.09.2024
 */

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStatusRepository projectStatusRepository;

    @Override
    public PageModel<ProjectModel> findAllProjects(final int pageNumber, final int pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final Page<ProjectModel> projects = projectRepository.findAll(pageable);
        return PageModel.<ProjectModel>builder()
                .projects(projects.toList())
                .totalElements(projects.getTotalElements())
                .build();
    }

    @Override
    public List<ProjectModel> findProjectsByOldestEntry(final int batchSize) {
        final Pageable pageable = PageRequest.of(0, batchSize, Sort.Direction.ASC, "lastSuccessScannerUpdate");
        return projectRepository.findAll(pageable).stream().toList();
    }

    @Override
    @Transactional
    public void saveProjectStatusRecord(final ProjectStatusRecordModel projectStatusRecord) {
        projectStatusRepository.save(projectStatusRecord);
        projectRepository.setLastSuccessScannerUpdate(new Timestamp(System.currentTimeMillis()),
                projectStatusRecord.getProject().getId());
    }
}
