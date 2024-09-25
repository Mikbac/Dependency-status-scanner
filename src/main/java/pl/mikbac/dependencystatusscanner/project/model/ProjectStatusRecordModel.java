package pl.mikbac.dependencystatusscanner.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MikBac on 04.05.2024
 */

@Entity
@Table(name = "project_status_record")
@Getter
@Setter
public class ProjectStatusRecordModel extends AbstractModel {

    private Integer openIssues;

    @ManyToOne
    @JoinColumn(name = "project_code", referencedColumnName = "projectCode")
    @JsonManagedReference
    private ProjectModel project;

}
