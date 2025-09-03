package com.devsu.querydatamanagement.application.service.mapper;


import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto.TransactionExtractEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ResponsesMapper {


    ClientResponse clientToClientResponse(Client client);

    @Mapping(source = "account.client", target = "personId", qualifiedByName = "mapClientId")
    AccountResponse accountToAccountResponse(Account account);

    @Mapping(source = "transactions.account", target = "accountId", qualifiedByName = "mapAccountId")
    @Mapping(source = "transactions.client", target = "personId", qualifiedByName = "mapPersonId")
    TransactionResponse transactionToTransactionResponse(Transactions transactions);

    @Mapping(source = "transactions.transactionDate", target = "createDate")
    @Mapping(source = "transactions.transactionType", target = "transactionType")
    @Mapping(source = "transactions.amount", target = "transactionAmount")
    @Mapping(source = "transactions.balance", target = "balance")
    @Mapping(source = "transactions.account.availableBalance", target = "initialBalance")
    @Mapping(source = "transactions.account.accountNumber", target = "accountNumber")
    @Mapping(source = "transactions.account.status", target = "status")
    @Mapping(source = "client.name", target = "clientName")
    TransactionExtractEvent mapToTransactionExtractEvent(Transactions transactions, Client client);



    @Named("mapClientId")
    default Long mapClientId(Client client) {
        return client.getPersonId();
    }

    @Named("mapAccountId")
    default Long mapAccountId(Account account) {
        return account.getAccountId();
    }

    @Named("mapPersonId")
    default Long mapPersonId(Client client) {
        return client.getPersonId();
    }


}
