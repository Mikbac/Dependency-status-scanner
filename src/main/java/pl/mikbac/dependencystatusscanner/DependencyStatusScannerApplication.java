package pl.mikbac.dependencystatusscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProjectsProperties.class)
public class DependencyStatusScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DependencyStatusScannerApplication.class, args);
    }

}
