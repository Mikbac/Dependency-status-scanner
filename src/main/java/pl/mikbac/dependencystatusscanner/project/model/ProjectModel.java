package pl.mikbac.dependencystatusscanner.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by MikBac on 14.04.2024
 */

@Entity
@Table(name = "project")
@Getter
@Setter
public class ProjectModel extends AbstractModel {

    private String projectCode;

    private String name;

    private String providerId;

    private String projectExternalId1;

    private String projectExternalId2;

    private String projectExternalId3;

    private Timestamp lastSuccessScannerUpdate;

    @OneToMany(mappedBy = "project")
    private Set<DependencyModel> dependencies;

    @OneToMany(mappedBy = "project")
    private Set<ProjectStatusRecordModel> projectStatusRecords;

}
