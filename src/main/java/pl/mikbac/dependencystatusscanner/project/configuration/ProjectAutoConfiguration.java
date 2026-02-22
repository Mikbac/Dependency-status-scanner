package pl.mikbac.dependencystatusscanner.project.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

import java.util.concurrent.Executor;

/**
 * Created by MikBac on 15.08.2025
 */

@AutoConfiguration
@ComponentScan("pl.mikbac.dependencystatusscanner.project")
@EntityScan("pl.mikbac.dependencystatusscanner.project.model")
@RequiredArgsConstructor
@EnableAsync
@EnableTransactionManagement
public class ProjectAutoConfiguration {

    private final ProjectsProperties projectsProperties;

    @Bean(name = "updateProjectsExecutor")
    public Executor updateProjectsExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadFactory(Thread.ofVirtual().name("updateProjectVThread-", 0).factory());

        executor.setCorePoolSize(projectsProperties.scanner().asyncExecutor().corePoolSize());
        executor.setMaxPoolSize(projectsProperties.scanner().asyncExecutor().maxPoolSize());
        executor.setQueueCapacity(projectsProperties.scanner().asyncExecutor().queueCapacity());

        executor.initialize();
        return executor;
    }

}
