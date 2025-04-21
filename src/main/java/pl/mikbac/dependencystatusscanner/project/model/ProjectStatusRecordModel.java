package pl.mikbac.dependencystatusscanner.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Created by MikBac on 04.05.2024
 */

@Entity
@Table(schema = "dependency_scanner", name = "project_status_records")
@Getter
@Setter
@Accessors(fluent = true)
public class ProjectStatusRecordModel extends AbstractModel {

    private Integer openIssues;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonManagedReference
    private ProjectModel project;

}
