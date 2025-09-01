package com.devsu.querydatamanagement.domain.interfaces;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;

import java.util.List;

public interface CommandWriteService {

    List<AccountResponse> loadAccountsData();

    List<TransactionResponse> loadTransactionsData();

    List<ClientResponse> loadClientsData();

    void SaveCustomer(ClientRequest clientRequest);
}
