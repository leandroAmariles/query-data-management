package com.devsu.querydatamanagement.application.service;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommandWriteServiceImpl implements CommandWriteService {

    private final ICustomerAdapter iCustomerAdapter;

    private final KafkaPublisherAdapter kafkaPublisherAdapter;


    @Override
    public List<AccountResponse> loadAccountsData() {
        return List.of();
    }

    @Override
    public List<TransactionResponse> loadTransactionsData() {
        return iCustomerAdapter.loadTransactionsData();
    }

    @Override
    public List<ClientResponse> loadClientsData() {
        return iCustomerAdapter.loadClientsData();
    }

    @Override
    public void SaveCustomer(ClientRequest clientRequest) {
        Client client = iCustomerAdapter.createClient(clientRequest);
        kafkaPublisherAdapter.updateCustomerInfoInReadSide(client);
    }




}
