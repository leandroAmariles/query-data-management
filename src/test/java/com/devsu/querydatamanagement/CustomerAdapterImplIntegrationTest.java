package com.devsu.querydatamanagement;

import com.devsu.querydatamanagement.domain.exceptions.ResourceExistException;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.adapter.CustomerAdapterImpl;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.AccountRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.ClientRepository;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.repository.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CustomerAdapterImplIntegrationTest {

    @Autowired
    private CustomerAdapterImpl customerAdapter;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    private ClientRequest baseClientReq;

    @BeforeEach
    void init() {
        baseClientReq = ClientRequest.builder()
                .identification("12345678")
                .name("Leandro")
                .build();
    }

    @Test
    void createClient_persists() throws Exception {
        var client = customerAdapter.createClient(baseClientReq);
        assertThat(client.getPersonId()).isNotNull();
        assertThat(client.getClientId()).isNotBlank();
        assertThat(client.isStatus()).isTrue();
        assertThat(clientRepository.findAll()).hasSize(1);
    }

    @Test
    void createClient_duplicateIdentification_throws() throws Exception {
        customerAdapter.createClient(baseClientReq);
        assertThatThrownBy(() -> customerAdapter.createClient(baseClientReq))
                .isInstanceOf(ResourceExistException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void createAccount_persists() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);

        AccountRequest accReq = AccountRequest.builder()
                .accountNumber("ACC-001")
                .personId(client.getPersonId())
                .accountType("AHORRO")
                .initialBalance(BigDecimal.valueOf(1000))
                .build();

        Account account = customerAdapter.createAccount(accReq);
        assertThat(account.getAccountId()).isNotNull();
        assertThat(account.isStatus()).isTrue();
        assertThat(accountRepository.findAll()).hasSize(1);
    }

    @Test
    void createAccount_duplicateNumber_throws() throws Exception {
        var client = customerAdapter.createClient(baseClientReq);

        AccountRequest accReq = AccountRequest.builder()
                .accountNumber("ACC-001")
                .personId(client.getPersonId())
                .accountType("AHORRO")
                .initialBalance(BigDecimal.valueOf(1000))
                .build();

        customerAdapter.createAccount(accReq);
        assertThatThrownBy(() -> customerAdapter.createAccount(accReq))
                .isInstanceOf(ResourceExistException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void createTransaction_deposit_updatesBalance() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);
        Account account = accountRepository.save(Account.builder()
                .accountNumber("ACC-002")
                .availableBalance(BigDecimal.valueOf(1000))
                        .accountType("AHORRO")
                        .initialBalance(BigDecimal.valueOf(1000))
                .client(client)
                .status(true)
                .build());

        TransactionRequest trxReq = TransactionRequest.builder()
                .personId(client.getPersonId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal(200))
                .transactionType("DEPOSITO")
                .build();

        Transactions trx = customerAdapter.createTransaction(trxReq);

        assertThat(trx.getTransactionId()).isNotNull();
        Account updated = accountRepository.findById(account.getAccountId()).orElseThrow();
        assertThat(updated.getAvailableBalance()).isEqualByComparingTo("1200");
        assertThat(transactionsRepository.findAll()).hasSize(1);
    }

    @Test
    void createTransaction_withdraw_updatesBalance() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);
        Account account = accountRepository.save(Account.builder()
                .accountNumber("ACC-003")
                .availableBalance(BigDecimal.valueOf(500))
                .accountType("TEST")
                .initialBalance(BigDecimal.valueOf(0))
                .client(client)
                .status(true)
                .build());

        TransactionRequest trxReq = TransactionRequest.builder()
                .personId(client.getPersonId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal(200))
                .transactionType("RETIRO")
                .build();

        customerAdapter.createTransaction(trxReq);
        Account updated = accountRepository.findById(account.getAccountId()).orElseThrow();
        assertThat(updated.getAvailableBalance()).isEqualByComparingTo("300");
    }

    @Test
    void createTransaction_invalidType_throws() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);
        Account account = accountRepository.save(Account.builder()
                .accountNumber("ACC-004")
                .availableBalance(BigDecimal.valueOf(1000))
                .client(client)
                        .accountType("INVALID")
                        .initialBalance(new BigDecimal(1000))
                .status(true)
                .build());

        TransactionRequest trxReq = TransactionRequest.builder()
                .personId(client.getPersonId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal(50))
                .transactionType("PAGO") // no permitido
                .build();

        assertThatThrownBy(() -> customerAdapter.createTransaction(trxReq))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not allowed");
    }

    @Test
    void validateBalance_insufficient_returnsFalse() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);
        Account account = accountRepository.save(Account.builder()
                .accountNumber("ACC-005")
                .availableBalance(BigDecimal.valueOf(100))
                        .accountType("TEST")
                        .initialBalance(BigDecimal.valueOf(1000))
                .client(client)
                .status(true)
                .build());

        boolean ok = customerAdapter.validateBalance(account.getAccountId(), BigDecimal.valueOf(200));
        assertThat(ok).isFalse();
    }

    @Test
    void deleteClient_setsStatusFalse() throws Exception {
        Client client = customerAdapter.createClient(baseClientReq);
        Client deleted = customerAdapter.deleteClientById(client.getPersonId());
        assertThat(deleted.isStatus()).isFalse();

        Client inDb = clientRepository.findById(client.getPersonId()).orElseThrow();
        assertThat(inDb.isStatus()).isFalse();
    }
}
