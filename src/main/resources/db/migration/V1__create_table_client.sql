CREATE TABLE client
(
    person_id BIGINT       NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NULL,
    status    BIT(1) NULL,
    CONSTRAINT pk_client PRIMARY KEY (person_id)
);

CREATE TABLE person
(
    person_id      BIGINT NOT NULL,
    name           VARCHAR(255) NULL,
    gender         VARCHAR(255) NULL,
    age            INT    NOT NULL,
    identification VARCHAR(255) NULL,
    address        VARCHAR(255) NULL,
    phone          VARCHAR(255) NULL,
    CONSTRAINT pk_person PRIMARY KEY (person_id)
);

ALTER TABLE client
    ADD CONSTRAINT uc_client_clientid UNIQUE (client_id);

ALTER TABLE client
    ADD CONSTRAINT FK_CLIENT_ON_PERSONID FOREIGN KEY (person_id) REFERENCES person (person_id);