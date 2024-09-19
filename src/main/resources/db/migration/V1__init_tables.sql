CREATE TYPE provider AS ENUM ('githubProvider');

CREATE TABLE project
(
    id                          UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    data_init                   TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_code                TEXT UNIQUE                         NOT NULL,
    name                        TEXT                                NOT NULL,
    provider_id                 provider                            NOT NULL,
    project_external_id1        TEXT,
    project_external_id2        TEXT,
    project_external_id3        TEXT,
    last_success_scanner_update TIMESTAMP
);


CREATE TABLE dependency
(
    id           UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    data_init    TIMESTAMP DEFAULT current_timestamp NOT NULL,
    code         TEXT                                NOT NULL,
    project_code TEXT                                NOT NULL,
    group_id     TEXT                                NOT NULL,
    artifact_id  TEXT                                NOT NULL,
    CONSTRAINT fk_project_dependency FOREIGN KEY (project_code) REFERENCES project (project_code)
);

CREATE TABLE project_status_record
(
    id           UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    data_init    TIMESTAMP DEFAULT current_timestamp NOT NULL,
    project_code TEXT                                NOT NULL,
    open_issues  NUMERIC,
    CONSTRAINT fk_project_status_record FOREIGN KEY (project_code) REFERENCES project (project_code)
);
