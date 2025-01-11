CREATE TYPE provider AS ENUM ('githubProvider');

CREATE TABLE projects
(
    id                   UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at           TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_code         TEXT UNIQUE                         NOT NULL,
    name                 TEXT                                NOT NULL,
    provider_code        provider                            NOT NULL,
    project_external_id1 TEXT,
    project_external_id2 TEXT,
    project_external_id3 TEXT,
    updated_at           TIMESTAMP
);

CREATE INDEX idx_projects_updated_at
    ON projects (updated_at);

CREATE TABLE dependencies
(
    id           UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at   TIMESTAMP DEFAULT current_timestamp NOT NULL,
    code         TEXT                                NOT NULL UNIQUE,
    project_id   UUID                                NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    dep_group    TEXT                                NOT NULL,
    dep_artifact TEXT                                NOT NULL
);

CREATE TABLE project_status_records
(
    id          UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_id  UUID                                NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    open_issues INTEGER
);
