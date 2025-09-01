CREATE TABLE account
(
    account_id        BIGINT       NOT NULL,
    account_number    VARCHAR(255) NOT NULL,
    account_type      VARCHAR(255) NULL,
    initial_balance   DECIMAL NULL,
    available_balance DECIMAL NULL,
    status            BIT(1)       NOT NULL,
    client_id         BIGINT       NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (account_id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_accountnumber UNIQUE (account_number);

ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_CLIENTID FOREIGN KEY (client_id) REFERENCES client (person_id);