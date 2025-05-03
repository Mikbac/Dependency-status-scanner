INSERT INTO projects (project_code, name, provider_code, project_external_id1, project_external_id2)
VALUES
    ('spring_framework', 'Spring Framework', 'GITHUB_PROVIDER', 'spring-projects', 'spring-framework'),
    ('spring_boot', 'Spring Boot', 'GITHUB_PROVIDER', 'spring-projects', 'spring-boot'),
    ('spring_security', 'Spring Security', 'GITHUB_PROVIDER', 'spring-projects', 'spring-security'),
    ('spring_ai', 'Spring AI', 'GITHUB_PROVIDER', 'spring-projects', 'spring-ai'),
    ('spring_shell', 'Spring Shell', 'GITHUB_PROVIDER', 'spring-projects', 'spring-shell'),
    ('spring_amqp', 'Spring AMQP', 'GITHUB_PROVIDER', 'spring-projects', 'spring-amqp'),
    ('spring_kafka', 'Spring for Apache Kafka', 'GITHUB_PROVIDER', 'spring-projects', 'spring-kafka');

INSERT INTO dependencies (code, project_id, dep_group, dep_artifact)
VALUES
    ('spring-boot-starter-web', (SELECT id FROM projects WHERE project_code = 'spring_boot'), 'org.springframework.boot', 'spring-boot-starter-web');
