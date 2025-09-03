package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.adapter;


import com.devsu.querydatamanagement.domain.exceptions.ResourceExistException;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.mapper.Mapper;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.AccountRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.ClientRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    @Transactional
    public Client createClient(ClientRequest client) throws Exception {
        Client clientEntity = mapper.dtoToEntity(client);
        if (existsByIdentification(clientEntity.getIdentification())) {
            throw new ResourceExistException("Client with identification already exists");
        }
        clientEntity.setClientId(UUID.randomUUID().toString());
        clientEntity.setStatus(true);
        return clientRepository.save(clientEntity);

    }

    @Override
    @Transactional
    public Client updateClient(ClientRequest clientRequest, String id) {
        Client clientEntity = clientRepository.findClientByPersonId(Long.parseLong(id))
                .orElseThrow(() -> new ResourceExistException("Client with id " + id + " does not exist"));
        mapper.updateEntityFromDto(clientRequest, clientEntity);
        return clientRepository.save(clientEntity);
    }

    @Override
    public Client deleteClientById(Long id) {
        Client client = clientRepository.findClientByPersonId(id)
                .orElseThrow(() -> new ResourceExistException("Client with id " + id + " does not exist"));
        client.setStatus(false);
        clientRepository.save(client);
        return client;
    }

    @Override
    @Transactional
    public Account createAccount(AccountRequest account) {
        Client client = clientRepository.findClientByPersonId((account.personId()))
                .orElseThrow(() -> new ResourceExistException("Client with id " + account.personId() + " does not exist"));
        if (accountRepository.existsAccountByAccountNumber(account.accountNumber())) {
            throw new ResourceExistException("Account with number " + account.accountNumber() + " already exists");
        }
        Account accountEntity = mapper.dtoToEntityAccount(account);
        accountEntity.setClient(client);
        accountEntity.setStatus(true);
        return accountRepository.save(accountEntity);
    }

    @Override
    @Transactional
    public Account updateAccount(AccountRequest account, String id) {
        Account accountEntity = accountRepository.findAccountByAccountId(Long.parseLong(id))
                .orElseThrow(() -> new ResourceExistException("Account with id " + account.accountId() + " does not exist"));
        mapper.updateEntityFromDtoAccount(account, accountEntity);
        return accountRepository.save(accountEntity);
    }


    @Override
    @Transactional
    public Transactions createTransaction(TransactionRequest transaction) {
        Client client = clientRepository.findClientByPersonId(transaction.personId())
                .orElseThrow(() -> new ResourceExistException("Client with id " + transaction.personId() + " does not exist"));
        Account account = accountRepository.findAccountByAccountId(transaction.accountId())
                .orElseThrow(() -> new ResourceExistException("Account with id " + transaction.accountId() + " does not exist"));
        Transactions transactions = mapper.dtoToEntityTransaction(transaction);
        transactions.setClient(client);
        BigDecimal initialBalance = account.getAvailableBalance();
        BigDecimal balance = calculateAvailableBalance(account, transactions);
        account.setAvailableBalance(balance);
        accountRepository.save(account);
        account.setAvailableBalance(initialBalance);
        transactions.setAccount(account);
        transactions.setBalance(balance.toString());
        return transactionsRepository.save(transactions);
    }

    @Override
    @Transactional
    public Transactions updateTransaction(TransactionRequest transaction, String id) {
        Transactions transactions = transactionsRepository.findByTransactionId(Long.valueOf(id))
                .orElseThrow(() -> new ResourceExistException("Transaction with id " + transaction.idTransaction() + " does not exist"));
        mapper.updateEntityFromDtoTransaction(transaction, transactions);
        return transactionsRepository.save(transactions);
    }

    @Override
    public Boolean validateBalance(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findAccountByAccountId(accountId).orElseThrow(
                () -> new ResourceExistException("Account with id " + accountId + " does not exist")
        );
        return validateBalance(account.getAvailableBalance(), amount);
    }

    boolean validateBalance(BigDecimal availableBalance, BigDecimal amount) {
        return availableBalance.compareTo(amount) >= 0;
    }


    boolean existsByIdentification(String identification) {
        return clientRepository.existsByIdentification(identification);
    }

    BigDecimal calculateAvailableBalance(Account account, Transactions transaction) {
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(transaction.getAmount()));
        return switch (transaction.getTransactionType()){
            case "COMPRA","RETIRO" -> account.getAvailableBalance().subtract(amount);
            case "DEPOSITO" -> account.getAvailableBalance().add(amount);
            default -> throw new IllegalStateException("Transaction type not allowed " + transaction.getTransactionType());
        };

    }


}
