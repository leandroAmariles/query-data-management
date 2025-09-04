package com.devsu.querydatamanagement;


import com.devsu.querydatamanagement.application.service.CommandWriteServiceImpl;
import com.devsu.querydatamanagement.application.service.mapper.ResponsesMapper;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.ICustomerAdapter;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CommandWriteServiceImplTest {

        private ICustomerAdapter iCustomerAdapter;
        private KafkaPublisherAdapter kafkaPublisherAdapter;
        private ResponsesMapper responsesMapper;
        private CommandWriteServiceImpl service;

        @BeforeEach
        void setUp() {
            iCustomerAdapter = mock(ICustomerAdapter.class);
            kafkaPublisherAdapter = mock(KafkaPublisherAdapter.class);
            responsesMapper = Mappers.getMapper(ResponsesMapper.class);
            service = new CommandWriteServiceImpl(iCustomerAdapter, kafkaPublisherAdapter);
        }

        @Test
        void givenSufficientBalance_whenCanProcessWithTransaction_thenReturnsTrue() {
            // given
            when(iCustomerAdapter.validateBalance(1L, BigDecimal.TEN)).thenReturn(true);

            // when
            boolean result = service.canProcessWithTransaction(1L, BigDecimal.TEN);

            // then
            assertThat(result).isTrue();
            verify(iCustomerAdapter).validateBalance(1L, BigDecimal.TEN);
            verifyNoMoreInteractions(iCustomerAdapter, kafkaPublisherAdapter);
        }

        @Test
        void givenValidRequest_whenSaveCustomer_thenReturnsResponseAndPublishesToKafka() throws Exception {
            // given
            ClientRequest request = ClientRequest.builder().build();
            Client client = new Client();
            when(iCustomerAdapter.createClient(request)).thenReturn(client);

            // when
            ClientResponse response = service.SaveCustomer(request);

            // then
            assertThat(response).isNotNull();
            verify(iCustomerAdapter).createClient(request);
            verify(kafkaPublisherAdapter).updateCustomerInfoInReadSide(client);
            verifyNoMoreInteractions(iCustomerAdapter, kafkaPublisherAdapter);
        }

        @Test
        void givenClientId_whenDeleteClient_thenInvokesAdapterAndKafka() {
            // when
            service.deleteClient(1L);

            // then
            verify(iCustomerAdapter).deleteClientById(1L);
            verify(kafkaPublisherAdapter).deleteCustomerInfoInReadSide(1L);
            verifyNoMoreInteractions(iCustomerAdapter, kafkaPublisherAdapter);
        }

        @Test
        void givenValidRequest_whenCreateAccount_thenReturnsResponseAndPublishesToKafka() throws Exception {
            // given
            AccountRequest request = AccountRequest.builder().build();
            Account account = new Account();
            account.setClient(new Client());
            when(iCustomerAdapter.createAccount(request)).thenReturn(account);

            // when
            AccountResponse response = service.createAccount(request);

            // then
            assertThat(response).isNotNull();
            verify(iCustomerAdapter).createAccount(request);
            verify(kafkaPublisherAdapter).updateAccountInfoInReadSide(response);
            verifyNoMoreInteractions(iCustomerAdapter, kafkaPublisherAdapter);
        }

        @Test
        void givenValidRequest_whenSaveTransaction_thenReturnsResponseAndPublishesToKafka() throws Exception {
            // given
            TransactionRequest request = TransactionRequest.builder().build();
            Transactions transaction = new Transactions();
            when(iCustomerAdapter.createTransaction(request)).thenReturn(transaction);

            // when
            TransactionResponse response = service.saveTransaction(request);

            // then
            assertThat(response).isNotNull();
            verify(iCustomerAdapter).createTransaction(request);
            verify(kafkaPublisherAdapter).updateTransactionInfoInReadSide(response);

        }
}
