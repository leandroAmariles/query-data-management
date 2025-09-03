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
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto.TransactionExtractEvent;
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
    public ClientResponse updateClient(ClientRequest clientRequest, String id) {
        Client client = iCustomerAdapter.updateClient(clientRequest, id);
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
        AccountResponse accountResponse = responsesMapper.accountToAccountResponse(account);
        kafkaPublisherAdapter.updateAccountInfoInReadSide(accountResponse);
        return accountResponse;
    }

    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest, String id) {
        Account account = iCustomerAdapter.updateAccount(accountRequest, id);
        AccountResponse accountResponse = responsesMapper.accountToAccountResponse(account);
        kafkaPublisherAdapter.updateAccountInfoInReadSide(accountResponse);
        return accountResponse;
    }

    @Override
    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) throws Exception {
        Transactions transactions = iCustomerAdapter.createTransaction(transactionRequest);
        TransactionResponse transactionResponse = responsesMapper.transactionToTransactionResponse(transactions);
        kafkaPublisherAdapter.updateTransactionInfoInReadSide(transactionResponse);
        TransactionExtractEvent transactionExtractEvent = responsesMapper.mapToTransactionExtractEvent(transactions, transactions.getClient());
        kafkaPublisherAdapter.updateTransactionsToExtractService(transactionExtractEvent);
        return transactionResponse;
    }

    @Override
    public TransactionResponse updateTransaction(TransactionRequest transactionRequest, String id) {
        Transactions transactions = iCustomerAdapter.updateTransaction(transactionRequest, id);
        TransactionResponse transactionResponse = responsesMapper.transactionToTransactionResponse(transactions);
        kafkaPublisherAdapter.updateTransactionInfoInReadSide(transactionResponse);
        return transactionResponse;
    }


}
