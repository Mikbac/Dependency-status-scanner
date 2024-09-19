package pl.mikbac.dependencystatusscanner.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.dependencystatusscanner.project.model.ProjectStatusRecordModel;

/**
 * Created by MikBac on 19.09.2024
 */

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatusRecordModel, String> {

}
