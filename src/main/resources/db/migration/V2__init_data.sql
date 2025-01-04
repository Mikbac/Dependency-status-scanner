INSERT INTO projects (project_code, name, provider_code, project_external_id1, project_external_id2)
VALUES
    ('spring_framework', 'Spring Framework', 'githubProvider', 'spring-projects', 'spring-framework'),
    ('spring_boot', 'Spring Boot', 'githubProvider', 'spring-projects', 'spring-boot'),
    ('spring_security', 'Spring Security', 'githubProvider', 'spring-projects', 'spring-security'),
    ('spring_ai', 'Spring AI', 'githubProvider', 'spring-projects', 'spring-ai'),
    ('spring_shell', 'Spring Shell', 'githubProvider', 'spring-projects', 'spring-shell'),
    ('spring_amqp', 'Spring AMQP', 'githubProvider', 'spring-projects', 'spring-amqp'),
    ('spring_kafka', 'Spring for Apache Kafka', 'githubProvider', 'spring-projects', 'spring-kafka');

INSERT INTO dependencies (code, project_id, dep_group, dep_artifact)
VALUES
    ('spring-boot-starter-web', (SELECT id FROM projects WHERE project_code = 'spring_boot'), 'org.springframework.boot', 'spring-boot-starter-web');
