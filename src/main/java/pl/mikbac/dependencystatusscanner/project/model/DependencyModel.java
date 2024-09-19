package pl.mikbac.dependencystatusscanner.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MikBac on 14.04.2024
 */

@Entity
@Table(name = "dependency")
@Getter
@Setter
public class DependencyModel extends AbstractModel {

    private String code;
    private String groupId;
    private String artifactId;

    @ManyToOne
    @JoinColumn(name = "project_code", referencedColumnName = "projectCode")
    private ProjectModel project;

}
