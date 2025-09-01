CREATE TABLE transactions
(
    transaction_id   BIGINT NOT NULL,
    transaction_date datetime NULL,
    transaction_type VARCHAR(255) NULL,
    amount           VARCHAR(255) NULL,
    balance          VARCHAR(255) NULL,
    account_id       BIGINT NOT NULL,
    person_id        BIGINT NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (transaction_id)
);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_ACCOUNTID FOREIGN KEY (account_id) REFERENCES account (account_id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_PERSON FOREIGN KEY (person_id) REFERENCES client (person_id);