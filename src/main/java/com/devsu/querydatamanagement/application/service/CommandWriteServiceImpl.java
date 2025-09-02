package com.devsu.querydatamanagement.application.service;

import com.devsu.querydatamanagement.application.service.mapper.ResponsesMapper;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
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

    @Override
    public ClientResponse updateClient(ClientRequest clientRequest) {
        Client client = iCustomerAdapter.updateClient(clientRequest);
        kafkaPublisherAdapter.updateCustomerInfoInReadSide(client);
        return responsesMapper.clientToClientResponse(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        iCustomerAdapter.deleteClientById(clientId);
        kafkaPublisherAdapter.deleteCustomerInfoInReadSide(clientId);
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) throws Exception {
        Account account = iCustomerAdapter.createAccount(accountRequest);
        kafkaPublisherAdapter.updateAccountInfoInReadSide(account);
        return responsesMapper.accountToAccountResponse(account);
    }

    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest) {
        Account account = iCustomerAdapter.updateAccount(accountRequest);
        kafkaPublisherAdapter.updateAccountInfoInReadSide(account);
        return responsesMapper.accountToAccountResponse(account);
    }

    @Override
    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) throws Exception {
        Transactions transactions = iCustomerAdapter.createTransaction(transactionRequest);
        kafkaPublisherAdapter.updateTransactionInfoInReadSide(transactions);
        return responsesMapper.transactionToTransactionResponse(transactions);
    }

    @Override
    public TransactionResponse updateTransaction(TransactionRequest transactionRequest) {
        Transactions transactions = iCustomerAdapter.updateTransaction(transactionRequest);
        kafkaPublisherAdapter.updateTransactionInfoInReadSide(transactions);
        return responsesMapper.transactionToTransactionResponse(transactions);
    }


}
