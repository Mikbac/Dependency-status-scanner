plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.dependency.management)
    alias(libs.plugins.cyclonedx.bom)
}

group = "pl.mikbac"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.flyway)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.validation)

    implementation(libs.spring.boot.starter.log4j2)
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }

    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.jackson.dataformat.yaml)

    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.postgresql)

    implementation(libs.resilience4j.spring.boot3)

    implementation(libs.apache.commons.lang3)

    implementation(platform(libs.micrometer.bom.get().toString()))
    implementation(libs.micrometer.core)
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.micrometer.registry.otlp)

    implementation(platform(libs.micrometer.tracing.bom.get().toString()))
    implementation(libs.micrometer.tracing)
    implementation(libs.micrometer.tracing.bridge.otel)

    implementation(libs.opentelemetry.exporter.otlp)

    implementation(libs.logbook.spring.boot.starter)

    compileOnly(libs.lombok)

    developmentOnly(libs.spring.boot.devtools)

    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgresql)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName = project.name
    archiveClassifier = "plain"
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = version
        attributes["Created-By"] = "mikbac"
        attributes["Main-Class"] = "pl.mikbac.dependencystatusscanner.DependencyStatusScannerApplication"
    }
}

tasks.register("fatJar", Jar::class) {
    group = "build"
    archiveBaseName = project.name
    archiveClassifier = "fat"
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = version
        attributes["Created-By"] = "mikbac"
        attributes["Main-Class"] = "pl.mikbac.dependencystatusscanner.DependencyStatusScannerApplication"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.cyclonedxDirectBom {
    projectType = org.cyclonedx.model.Component.Type.APPLICATION
    componentVersion = project.version.toString()

    schemaVersion = org.cyclonedx.Version.VERSION_16
    xmlOutput.unsetConvention()

    includeConfigs = listOf("runtimeClasspath")
    skipConfigs = listOf("testRuntimeClasspath")

    includeBomSerialNumber = true
    includeLicenseText = true
    includeMetadataResolution = true
    includeBuildSystem = true
}
