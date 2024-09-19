package pl.mikbac.dependencystatusscanner.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by MikBac on 11.08.2024
 */

@ConfigurationProperties("projects")
public record ProjectsProperties(ScannerProperties scanner,
                                 ProviderProperties provider) {
    public record ScannerProperties(int batchSize) {
    }

    public record ProviderProperties(GithubProperties github) {
        public record GithubProperties(String url,
                                       String token,
                                       int connectionTimeout,
                                       int readTimeout) {
        }
    }
}
