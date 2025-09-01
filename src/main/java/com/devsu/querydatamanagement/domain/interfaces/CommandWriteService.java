package com.devsu.querydatamanagement.domain.interfaces;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;

import java.math.BigDecimal;
import java.util.List;

public interface CommandWriteService {

    boolean canProcessWithTransaction(Long accountId, BigDecimal amount);

    List<AccountResponse> loadAccountsData();

    List<TransactionResponse> loadTransactionsData();

    List<ClientResponse> loadClientsData();

    ClientResponse SaveCustomer(ClientRequest clientRequest) throws Exception;
}
