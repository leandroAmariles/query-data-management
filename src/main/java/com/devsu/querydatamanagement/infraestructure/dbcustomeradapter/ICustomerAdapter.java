package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter;


import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerAdapter {

    List<AccountResponse> loadAccountsData();

    List<TransactionResponse> loadTransactionsData();

    List<ClientResponse> loadClientsData();

    Client createClient(ClientRequest client) throws Exception;

    Client updateClient(ClientRequest client, String id);

    Client deleteClientById(Long id);

    Account createAccount(AccountRequest account);

    Account updateAccount(AccountRequest account, String id);

    Transactions createTransaction(TransactionRequest transaction);

    Transactions updateTransaction(TransactionRequest transaction, String id);

    Boolean validateBalance(Long accountId, BigDecimal amount);

}
