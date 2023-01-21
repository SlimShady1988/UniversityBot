CREATE TYPE degree_type AS ENUM ('assistant', 'associate professor', 'professor');

CREATE TABLE IF NOT EXISTS departments
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    name            TEXT NOT NULL UNIQUE
);

CREATE TABLE degree
(
    id               BIGSERIAL PRIMARY KEY NOT NULL,
    name             degree_type NOT NULL
);


CREATE TABLE lectors
(
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    firstname               TEXT NOT NULL,
    lastname                TEXT NOT NULL,
    payment                 BIGINT NOT NULL,
    head_of_department      BIGINT constraint department_id_constraint references departments,
                            FOREIGN KEY (head_of_department) REFERENCES departments(id),
    degree_id               BIGSERIAL NOT NULL constraint degree_id_constraint references degree,
                            FOREIGN KEY (degree_id) REFERENCES degree(id)
);

CREATE TABLE lectors_departments
(
    lector_id               BIGSERIAL NOT NULL
        constraint fk_lectors_departments_lector_id REFERENCES lectors,
    department_id           BIGSERIAL NOT NULL
        constraint fk_departments_lector_departments_id REFERENCES departments
);

ALTER TABLE lectors
    ADD CONSTRAINT uq_lectors UNIQUE(firstname, lastname);