package pl.mikbac.dependencystatusscanner.project.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by MikBac on 04.05.2024
 */

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private Timestamp dataInit;

}
