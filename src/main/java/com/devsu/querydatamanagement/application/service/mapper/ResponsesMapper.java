package com.devsu.querydatamanagement.application.service.mapper;


import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import org.mapstruct.Mapper;

@Mapper
public interface ResponsesMapper {


    ClientResponse clientToClientResponse(Client client);

    AccountResponse accountToAccountResponse(Account account);

    TransactionResponse transactionToTransactionResponse(Transactions transactions);
}
