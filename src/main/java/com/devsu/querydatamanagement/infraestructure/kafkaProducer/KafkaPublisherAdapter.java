package com.devsu.querydatamanagement.infraestructure.kafkaProducer;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto.TransactionExtractEvent;

public interface KafkaPublisherAdapter {

    void updateCustomerInfoInReadSide(Client client);

    void deleteCustomerInfoInReadSide(Long clientId);

    void updateAccountInfoInReadSide(AccountResponse account);

    void updateTransactionInfoInReadSide(TransactionResponse transactions);

    void updateTransactionsToExtractService(TransactionExtractEvent transaction);
}
