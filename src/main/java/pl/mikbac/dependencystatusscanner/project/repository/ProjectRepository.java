package pl.mikbac.dependencystatusscanner.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mikbac.dependencystatusscanner.project.model.ProjectModel;
import pl.mikbac.dependencystatusscanner.project.model.UpdateStatus;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by MikBac on 03.05.2024
 */

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, String> {

    @Modifying
    @Query("UPDATE ProjectModel p SET p.updatedAt = :scannerUpdatedAt, p.lastUpdateStatus =:status WHERE p.id = :id")
    void setScannerUpdate(@Param("scannerUpdatedAt") Timestamp scannerUpdatedAt,
                          @Param("status") UpdateStatus status,
                          @Param("id") UUID id);

    @EntityGraph(attributePaths = {"dependencies", "projectStatusRecords"})
    Optional<ProjectModel> findFirstByProjectCode(String projectCode);

    @EntityGraph(attributePaths = {"dependencies", "projectStatusRecords"})
    Page<ProjectModel> findAll(Pageable pageable);
}
