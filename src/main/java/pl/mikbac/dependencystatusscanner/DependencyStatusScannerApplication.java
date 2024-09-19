package pl.mikbac.dependencystatusscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProjectsProperties.class)
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
public class DependencyStatusScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DependencyStatusScannerApplication.class, args);
    }

}
