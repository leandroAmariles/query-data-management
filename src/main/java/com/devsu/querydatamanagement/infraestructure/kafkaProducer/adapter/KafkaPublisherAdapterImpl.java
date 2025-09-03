package com.devsu.querydatamanagement.infraestructure.kafkaProducer.adapter;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto.TransactionExtractEvent;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto.UpdateInfoCustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPublisherAdapterImpl implements KafkaPublisherAdapter {

    private final KafkaTemplate<String, String> kafkaTemplate;
    
    private final ObjectMapper objectMapper;


@Override
    public void updateCustomerInfoInReadSide(Client client) {
        if (log.isTraceEnabled()) {
            log.trace("updateCustomerInfoInReadSide(client={})", client);
        }


        StringBuilder hashKey = new StringBuilder();
        hashKey.append("client-");
        hashKey.append(client.getPersonId());

    UpdateInfoCustomerDto dto = UpdateInfoCustomerDto.builder()
            .name(client.getName()!=null?client.getName():null)
            .age(client.getAge())
            .phone(client.getPhone()!=null?client.getPhone():null)
            .password(client.getPassword()!=null?client.getPassword():null)
            .status(client.isStatus())
            .clientId(client.getClientId()!=null?client.getClientId():null)
            .identification(client.getIdentification()!=null?client.getIdentification():null)
            .address(client.getAddress()!=null?client.getAddress():null)
            .accounts(client.getAccounts()!=null?client.getAccounts().stream().map(Account::getAccountId).toList():null)
            .build();

        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("customer-updates", hashKey.toString(), json);
            log.info("updateCustomerInfoInReadSide(client={})", client);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error("Error serializando cliente a JSON", e);
        }
    }

    @Override
    public void deleteCustomerInfoInReadSide(Long clientId) {
        if (log.isTraceEnabled()) {
            log.trace("update customer info {}", clientId);
        }
        StringBuilder hashKey = new StringBuilder();
        hashKey.append("client-");
        hashKey.append(clientId);

        kafkaTemplate.send("customer-deletes", hashKey.toString(), null);
    }

    @Override
    public void updateAccountInfoInReadSide(AccountResponse account) {
        if (log.isTraceEnabled()) {
            log.trace("update account info {}", account);
        }
        StringBuilder hashkey = new StringBuilder();
        hashkey.append("account-");
        hashkey.append(account.accountId());
        try {
            kafkaTemplate.send("account-updates", hashkey.toString(), objectMapper.writeValueAsString(account));
        }catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error("Error serializando cuenta a JSON", e);
        }
    }

    @Override
    public void updateTransactionInfoInReadSide(TransactionResponse transactions) {
        if (log.isTraceEnabled()) {
            log.trace("update transaction info {}", transactions);
        }
        StringBuilder hashkey = new StringBuilder();
        hashkey.append("transaction-");
        hashkey.append(transactions.transactionId());
        try{
        kafkaTemplate.send("transaction-updates", hashkey.toString(), objectMapper.writeValueAsString(transactions));
        }catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error("Error serializando transaccion a JSON", e);
        }
    }

    @Override

    public void updateTransactionsToExtractService(TransactionExtractEvent transaction) {
        log.info("update transaction extract info {}", transaction);
        StringBuilder hashkey = new StringBuilder();
        hashkey.append("transaction-extract-");
        hashkey.append(new Random().nextInt(999999));

        try {
            kafkaTemplate.send("transaction-audit-updater", hashkey.toString(), objectMapper.writeValueAsString(transaction));
        } catch (JsonProcessingException e) {
            log.error("Error serializando transaccion a JSON", e);
        }
    }


}
