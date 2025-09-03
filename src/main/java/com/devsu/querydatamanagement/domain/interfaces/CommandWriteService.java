package com.devsu.querydatamanagement.domain.interfaces;

import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CommandWriteService {

    boolean canProcessWithTransaction(Long accountId, BigDecimal amount);

    List<AccountResponse> loadAccountsData();

    List<TransactionResponse> loadTransactionsData();

    List<ClientResponse> loadClientsData();

    ClientResponse SaveCustomer(ClientRequest clientRequest) throws Exception;

    ClientResponse updateClient(ClientRequest clientRequest, String id);

    void deleteClient(Long clientId);

    AccountResponse createAccount(AccountRequest accountRequest) throws Exception;

    AccountResponse updateAccount(AccountRequest accountRequest, String id);

    TransactionResponse saveTransaction(TransactionRequest transactionRequest) throws Exception;

    TransactionResponse updateTransaction(TransactionRequest transactionRequest, String id);
}
