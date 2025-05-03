package pl.mikbac.dependencystatusscanner.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mikbac.dependencystatusscanner.project.model.DependencyModel;

import java.util.Optional;

/**
 * Created by MikBac on 19.09.2024
 */

@Repository
public interface DependencyRepository extends JpaRepository<DependencyModel, String> {

    Optional<DependencyModel> findByCode(String code);
}
