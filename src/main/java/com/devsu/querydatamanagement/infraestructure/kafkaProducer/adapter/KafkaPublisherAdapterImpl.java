package com.devsu.querydatamanagement.infraestructure.kafkaProducer.adapter;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPublisherAdapterImpl implements KafkaPublisherAdapter {

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void updateCustomerInfoInReadSide(Client client) {
        if (log.isTraceEnabled()) {
            log.trace("updateCustomerInfoInReadSide(client={})", client);
        }
        StringBuilder hashKey = new StringBuilder();
        hashKey.append("client-");
        hashKey.append(client.getClientId());

        kafkaTemplate.send("customer-updates", hashKey.toString(),new Gson().toJson(client));
    }

    @Override
    public void deleteCustomerInfoInReadSide(Long clientId) {
        if (log.isTraceEnabled()) {
            log.trace("update customer info {}", clientId);
        }
        StringBuilder hashKey = new StringBuilder();
        hashKey.append("client-");
        hashKey.append(clientId);
        hashKey.append("-deleted");

        kafkaTemplate.send("customer-deletes", hashKey.toString(), null);
    }

    @Override
    public void updateAccountInfoInReadSide(Account account) {
        if (log.isTraceEnabled()) {
            log.trace("update account info {}", account);
        }

        StringBuilder hashkey = new StringBuilder();
        hashkey.append("account-");
        hashkey.append(account.getAccountId());
        kafkaTemplate.send("account-updates", hashkey.toString(), new Gson().toJson(account));
    }

    @Override
    public void updateTransactionInfoInReadSide(Transactions transactions) {
        if (log.isTraceEnabled()) {
            log.trace("update transaction info {}", transactions);
        }
        StringBuilder hashkey = new StringBuilder();
        hashkey.append("transaction-");
        hashkey.append(transactions.getTransactionId());
        kafkaTemplate.send("transaction-updates", hashkey.toString(), new Gson().toJson(transactions));
    }


}
