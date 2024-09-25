package pl.mikbac.dependencystatusscanner.provider.impl.github;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import pl.mikbac.dependencystatusscanner.properties.ProjectsProperties;

import java.time.Duration;

/**
 * Created by MikBac on 17.08.2024
 */

@Configuration
@ConditionalOnProperty(prefix = "projects.provider.github", name = "active")
public class GitHubProviderConfiguration {

    public static final String GITHUB_PROVIDER = "githubProvider";
    public static final String GITHUB_CLIENT = "githubClient";
    public static final String GITHUB_PROVIDER_REST_CLIENT = "githubRestClient";
    public static final String GITHUB_PROVIDER_REQUEST_FACTORY = "githubRequestFactory";

    @Bean(GITHUB_CLIENT)
    public GitHubClient githubClient(@Qualifier(GITHUB_PROVIDER_REST_CLIENT) final RestClient githubRestClient) {
        return new GitHubClient(githubRestClient);
    }

    @Bean(GITHUB_PROVIDER_REST_CLIENT)
    public RestClient githubRestClient(@Qualifier(GITHUB_PROVIDER_REQUEST_FACTORY) final ClientHttpRequestFactory requestFactory,
                                       final ProjectsProperties properties) {
        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(properties.provider().github().url())
                .defaultHeaders(hh -> {
                    final String token = properties.provider().github().token();
                    if (StringUtils.isNotBlank(token)) {
                        hh.setBearerAuth(token);
                    }
                })
                .build();
    }

    @Bean(GITHUB_PROVIDER_REQUEST_FACTORY)
    public ClientHttpRequestFactory githubRequestFactory(final ProjectsProperties properties) {
        final var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(properties.provider().github().connectionTimeout()));
        requestFactory.setReadTimeout(Duration.ofSeconds(properties.provider().github().readTimeout()));
        return requestFactory;
    }

}
