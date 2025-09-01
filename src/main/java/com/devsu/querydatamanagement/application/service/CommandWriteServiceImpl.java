package com.devsu.querydatamanagement.application.service;

import com.devsu.querydatamanagement.application.service.mapper.ResponsesMapper;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommandWriteServiceImpl implements CommandWriteService {

    private final ICustomerAdapter iCustomerAdapter;

    private final KafkaPublisherAdapter kafkaPublisherAdapter;

    private final ResponsesMapper responsesMapper = Mappers.getMapper(ResponsesMapper.class);


    @Override
    public boolean canProcessWithTransaction(Long accountId, BigDecimal amount) {
        return iCustomerAdapter.validateBalance(accountId, amount);
    }

    @Override
    public List<AccountResponse> loadAccountsData() {
        return iCustomerAdapter.loadAccountsData();
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
    public ClientResponse SaveCustomer(ClientRequest clientRequest) throws Exception {
        Client client = iCustomerAdapter.createClient(clientRequest);
        kafkaPublisherAdapter.updateCustomerInfoInReadSide(client);
        return responsesMapper.clientToClientResponse(client);
    }




}
