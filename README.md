# Dependency status scanner

## Providers

| Id               | Description                       | Parameters                                                                                    |
|------------------|-----------------------------------|-----------------------------------------------------------------------------------------------|
| `githubProvider` | Project data provider from GitHub | `project_provider_id_1` - GitHub account<br> `project_provider_id_2` - GitHub repository name |

## Postgres & ELK

### Docker compose

```shell
docker compose -f ./docker/postgres.yaml up -d
docker compose -f ./docker/elk.yaml up -d
```

### Kibana

Logs are passed from the application to Filebeat and then to Logstash. Logstash provides logs to Elasticsearch with
index pattern:
`%{[@metadata][beat]}-%{[@metadata][version]}-%{[log_info][project_origin]}-%{+YYYY-MM-dd}` e.g.
`filebeat-8.15.1-dependency_status_scanner-*` (`filebeat-8.15.1-dependency_status_scanner-2024-12-27`).

Kibana is available via http://localhost:5601/. A sample data view is included in the `kibana`
catalog ([Kibana-data-view](kibana/Kibana-data-view.ndjson)).

![kibana.png](img/kibana.png)

## DB diagram

```mermaid
graph TD;
    project-->dependency;
    project-->project_status_record;
```

## Metrics

Metrics are available via:

* Health: http://localhost:8080/actuator/health
* Flyway: http://localhost:8080/actuator/flyway
* Prometheus: http://localhost:8080/actuator/prometheus

## Building container image

Building a container image (multistage build with copied gradle wrapper to use the same version of gradle):

```shell
docker build -t mikbac/dependency-status-scanner:1.0 .
```

Running docker compose app (with postgres and ELK stack):

```shell
docker compose -f ./docker/dsc.yaml up -d
```

## Upgrading gradle version

```shell
./gradlew wrapper --gradle-version <version>
# e.g.
./gradlew wrapper --gradle-version 8.12
```

## TODO

* [ ] Circuit Breaker Resillience4j
* [ ] OpenApi
* [ ] Swagger
* [ ] Hateos
* [ ] Redis
* [ ] Grafana
* [ ] Split providers to modules + convention plugins
* [ ] Support for Cassandra/ScyllaDB
* [ ] Spring security - token
* [ ] BOM file import
* [ ] ArchUnit test
* [ ] GitLab connector
* [ ] Jacoco
* [ ] CQRS
* [ ] PMD gradle plugin
* [ ] OWASP dependency-check gradle plugin
* [ ] Automatically build and run unit tests
* [ ] Automatic deployment to dockerhub
* [ ] Embedded postgres for testing
