FROM eclipse-temurin:21-jdk as builder

RUN mkdir /app
WORKDIR /app

COPY gradle ./gradle
COPY src ./src
COPY gradlew build.gradle.kts settings.gradle.kts lombok.config ./

RUN ./gradlew bootJar

FROM eclipse-temurin:21-jre

LABEL org.opencontainers.image.authors="MikBac" \
      org.opencontainers.image.source="https://github.com/Mikbac/Dependency-status-scanner" \
      org.opencontainers.image.description="Dependency status scanner."

RUN mkdir /opt/app && \
    groupadd -g 10001 dsc && \
    useradd -u 10000 -g dsc dsc && \
    chown -R dsc:dsc /opt/app

WORKDIR /opt/app

COPY --from=builder /app/build/libs/Dependency-status-scanner-*.jar app.jar

EXPOSE 8080

USER dsc:dsc

HEALTHCHECK --interval=1m --timeout=10s --retries=5 \
  CMD curl -f http://localhost:8081/actuator/health/readiness || exit 1

ENTRYPOINT ["java","-jar","/opt/app/app.jar"]
