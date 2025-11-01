FROM eclipse-temurin:21-jdk AS builder

RUN mkdir /app
WORKDIR /app

COPY gradle ./gradle
COPY gradlew build.gradle.kts settings.gradle.kts lombok.config libs.versions.toml ./
COPY src ./src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy

LABEL org.opencontainers.image.authors="MikBac" \
      org.opencontainers.image.source="https://github.com/Mikbac/Dependency-status-scanner" \
      org.opencontainers.image.description="Dependency status scanner."

RUN mkdir /opt/app && \
    groupadd -g 10001 dss && \
    useradd -u 10000 -g dss -M -s /sbin/nologin dss && \
    chown -R dss:dss /opt/app

WORKDIR /opt/app

COPY --from=builder /app/build/libs/Dependency-status-scanner-*.jar app.jar

EXPOSE 8080

USER dss:dss

HEALTHCHECK --interval=1m --timeout=10s --retries=5 \
  CMD curl -f http://localhost:8081/actuator/health/readiness || exit 1

ENTRYPOINT ["java"]
CMD ["-XX:InitialRAMPercentage=25.0", "-XX:MinRAMPercentage=25.0", "-XX:MaxRAMPercentage=75.0", "-jar", "/opt/app/app.jar"]
