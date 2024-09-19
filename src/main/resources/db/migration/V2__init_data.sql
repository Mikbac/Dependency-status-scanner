INSERT INTO Project
VALUES
    (DEFAULT, DEFAULT, 'spring_framework', 'Spring Framework', 'githubProvider', 'spring-projects', 'spring-framework', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_boot', 'Spring Boot', 'githubProvider', 'spring-projects', 'spring-boot', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_security', 'Spring Security', 'githubProvider', 'spring-projects', 'spring-security', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_ai', 'Spring AI', 'githubProvider', 'spring-projects', 'spring-ai', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_shell', 'Spring Shell', 'githubProvider', 'spring-projects', 'spring-shell', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_amqp', 'Spring AMQP', 'githubProvider', 'spring-projects', 'spring-amqp', NULL, NULL),
    (DEFAULT, DEFAULT, 'spring_kafka', 'Spring for Apache Kafka', 'githubProvider', 'spring-projects', 'spring-kafka', NULL, NULL);

INSERT INTO Dependency
VALUES
    (DEFAULT, DEFAULT, 'spring-boot-starter-web', 'spring_boot', 'org.springframework.boot', 'spring-boot-starter-web');
