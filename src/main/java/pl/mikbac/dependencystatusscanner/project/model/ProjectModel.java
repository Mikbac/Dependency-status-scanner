package pl.mikbac.dependencystatusscanner.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.sql.Timestamp;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Created by MikBac on 14.04.2024
 */

@Entity
@Table(name = "projects")
@Getter
@Setter
@Accessors(fluent = true)
public class ProjectModel extends AbstractModel {

    private String projectCode;

    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Provider providerCode;

    private String projectExternalId1;

    private String projectExternalId2;

    private String projectExternalId3;

    @CreationTimestamp
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UpdateStatus lastUpdateStatus;

    @OneToMany(mappedBy = "project", fetch = LAZY)
    @JsonBackReference
    private Set<DependencyModel> dependencies;

    @OneToMany(mappedBy = "project", fetch = LAZY)
    @JsonBackReference
    private Set<ProjectStatusRecordModel> projectStatusRecords;

}
