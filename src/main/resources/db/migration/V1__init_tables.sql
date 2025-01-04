CREATE TYPE provider AS ENUM ('githubProvider');

CREATE TABLE projects
(
    id                          UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    init_data                   TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_code                TEXT UNIQUE                         NOT NULL,
    name                        TEXT                                NOT NULL,
    provider_code               provider                            NOT NULL,
    project_external_id1        TEXT,
    project_external_id2        TEXT,
    project_external_id3        TEXT,
    last_success_scanner_update TIMESTAMP
);

CREATE INDEX idx_projects_last_success_scanner_update
    ON projects (last_success_scanner_update);

CREATE TABLE dependencies
(
    id           UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    init_data    TIMESTAMP DEFAULT current_timestamp NOT NULL,
    code         TEXT                                NOT NULL,
    project_id   UUID                                NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    dep_group    TEXT                                NOT NULL,
    dep_artifact TEXT                                NOT NULL
);

CREATE TABLE project_status_records
(
    id          UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    init_data   TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_id  UUID                                NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    open_issues NUMERIC
);
