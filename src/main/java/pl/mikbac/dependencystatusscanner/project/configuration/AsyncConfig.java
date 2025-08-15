package pl.mikbac.dependencystatusscanner.project.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

import java.util.concurrent.Executor;

/**
 * Created by MikBac on 15.08.2025
 */

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

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
