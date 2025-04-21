plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.4"
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")

    implementation("org.flywaydb:flyway-core:10.12.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.12.0")
    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.apache.commons:commons-lang3:3.17.0")

    implementation("io.micrometer:micrometer-registry-prometheus:1.13.6")

    compileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
