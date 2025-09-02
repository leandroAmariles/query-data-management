package com.devsu.querydatamanagement.infraestructure.kafkaProducer;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;

public interface KafkaPublisherAdapter {

    void updateCustomerInfoInReadSide(Client client);

    void deleteCustomerInfoInReadSide(Long clientId);

    void updateAccountInfoInReadSide(Account account);

    void updateTransactionInfoInReadSide(Transactions transactions);
}
