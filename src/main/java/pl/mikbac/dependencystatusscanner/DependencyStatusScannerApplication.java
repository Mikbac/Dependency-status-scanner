package pl.mikbac.dependencystatusscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

@SpringBootApplication(scanBasePackages = {
        "pl.mikbac.dependencystatusscanner.project",
        "pl.mikbac.dependencystatusscanner.scanner",
        "pl.mikbac.dependencystatusscanner.provider"
})
@EntityScan(basePackages = "pl.mikbac.dependencystatusscanner.project.model")
@EnableConfigurationProperties(ProjectsProperties.class)
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
public class DependencyStatusScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DependencyStatusScannerApplication.class, args);
    }

}
