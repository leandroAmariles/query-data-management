CREATE TABLE ACCOUNT_DEVSU
(
    accountId        BIGINT NOT NULL AUTO_INCREMENT,
    accountNumber    VARCHAR(255) NOT NULL,
    accountType      VARCHAR(255) NULL,
    initialBalance   DECIMAL NULL,
    availableBalance DECIMAL NULL,
    status            BIT(1)       NOT NULL,
    clientId         BIGINT       NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (accountId)
);

ALTER TABLE ACCOUNT_DEVSU
    ADD CONSTRAINT uc_account_accountnumber UNIQUE (accountNumber);

ALTER TABLE ACCOUNT_DEVSU
    ADD CONSTRAINT FK_ACCOUNT_ON_CLIENTID FOREIGN KEY (clientId) REFERENCES CLIENT_DEVSU (personId);