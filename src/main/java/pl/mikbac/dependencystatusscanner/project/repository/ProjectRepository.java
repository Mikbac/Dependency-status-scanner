package pl.mikbac.dependencystatusscanner.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by MikBac on 03.05.2024
 */

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, String> {

    @Modifying
    @Query("UPDATE ProjectModel p SET p.lastSuccessScannerUpdate = :lastSuccessScannerUpdate WHERE p.id = :id")
    void setLastSuccessScannerUpdate(@Param("lastSuccessScannerUpdate") Timestamp lastSuccessScannerUpdate,
                                     @Param("id") UUID id);

}
