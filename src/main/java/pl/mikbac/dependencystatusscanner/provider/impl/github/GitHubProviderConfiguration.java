package pl.mikbac.dependencystatusscanner.provider.impl.github;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
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
    public static final String GITHUB_PROVIDER_REST_TEMPLATE = "githubRestTemplate";

    @Bean(GITHUB_CLIENT)
    public GitHubClient githubClient(@Qualifier(GITHUB_PROVIDER_REST_TEMPLATE) final RestTemplate githubRestTemplate) {
        return new GitHubClient(githubRestTemplate);
    }

    @Bean(GITHUB_PROVIDER_REST_TEMPLATE)
    public RestTemplate githubRestTemplate(final RestTemplateBuilder restTemplateBuilder,
                                           final ProjectsProperties properties) {
        return restTemplateBuilder
                .rootUri(properties.provider().github().url())
                .setConnectTimeout(Duration.ofSeconds(properties.provider().github().connectionTimeout()))
                .setReadTimeout(Duration.ofSeconds(properties.provider().github().readTimeout()))
                .additionalInterceptors((request, body, execution) -> {
                    final String token = properties.provider().github().token();
                    if (StringUtils.isNotBlank(token)) {
                        request.getHeaders().add("Authorization", "Bearer " + token);
                    }
                    return execution.execute(request, body);
                })
                .build();
    }

}
