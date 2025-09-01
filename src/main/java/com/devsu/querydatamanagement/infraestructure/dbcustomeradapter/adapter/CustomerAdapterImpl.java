package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.adapter;


import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.mapper.Mapper;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.AccountRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.ClientRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAdapterImpl implements ICustomerAdapter {

    private final ClientRepository clientRepository;
    private final TransactionsRepository transactionsRepository;
    private final AccountRepository accountRepository;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);


    @Override
    public List<AccountResponse> loadAccountsData() {
        List<Account> accounts = accountRepository.findAll();
        return mapper.entityListToDtoAccount(accounts);
    }

    @Override
    public List<TransactionResponse> loadTransactionsData() {
        List<Transactions> transactions = transactionsRepository.findAll();
        return mapper.entityListToDtoTransaction(transactions);
    }

    @Override
    public List<ClientResponse> loadClientsData() {
        List<Client> clients = clientRepository.findAll();
        return mapper.entityListToDto(clients);
    }

    @Override
    public Client createClient(ClientRequest client) throws Exception {
        Client clientEntity = mapper.dtoToEntity(client);
        if (!existsByIdentification(clientEntity.getIdentification())) {
            throw new Exception();
        }
        return clientRepository.save(clientEntity);

    }

    @Override
    public Client updateClient(ClientRequest clientRequest) {
        Client clientEntity = clientRepository.findByClientId(clientRequest.clientId()).orElseThrow();
        mapper.updateEntityFromDto(clientRequest, clientEntity);
        return clientRepository.save(clientEntity);
    }

    @Override
    public void deleteClientById(Long id) {
        Client client = clientRepository.findByClientId(String.valueOf(id)).orElseThrow();
        client.setStatus(false);
        clientRepository.save(client);
    }

    @Override
    public Account createAccount(AccountRequest account) {
        Client client = clientRepository.findByClientId(String.valueOf(account.clientId())).orElseThrow();
        Account accountEntity = mapper.dtoToEntityAccount(account);
        accountEntity.setClient(client);
        return accountRepository.save(accountEntity);
    }

    @Override
    public Account updateAccount(AccountRequest account) {
        Account accountEntity = accountRepository.findAccountByAccountId(Long.valueOf(account.accountId())).orElseThrow();
        mapper.updateEntityFromDtoAccount(account, accountEntity);
        return accountRepository.save(accountEntity);
    }


    @Override
    public Transactions createTransaction(TransactionRequest transaction) {
        Client client = clientRepository.findByClientId(String.valueOf(transaction.idClient())).orElseThrow();
        Account account = accountRepository.findAccountByAccountId(transaction.idAccount()).orElseThrow();
        Transactions transactions = mapper.dtoToEntityTransaction(transaction);
        transactions.setClient(client);
        transactions.setAccount(account);
        return transactionsRepository.save(transactions);
    }

    @Override
    public Transactions updateTransaction(TransactionRequest transaction) {
        Transactions transactions = transactionsRepository.findByTransactionId(transaction.idTransaction()).orElseThrow();
        mapper.updateEntityFromDtoTransaction(transaction, transactions);
        return transactionsRepository.save(transactions);
    }

    @Override
    public Boolean validateBalance(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findAccountByAccountId(accountId).orElseThrow();
        return validateBalance(account.getAvailableBalance(), amount);
    }

    boolean validateBalance(BigDecimal availableBalance, BigDecimal amount) {
        return availableBalance.compareTo(amount) >= 0;
    }


    boolean existsByIdentification(String identification) {
        return clientRepository.existsByIdentification(identification);
    }


}
