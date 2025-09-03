package com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.mapper;

import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {


    @Mapping(source = "clientRequest.name", target = "name")
    @Mapping(source = "clientRequest.gender", target = "gender")
    @Mapping(source = "clientRequest.age", target = "age")
    @Mapping(source = "clientRequest.address", target = "address")
    @Mapping(source = "clientRequest.phone", target = "phone")
    @Mapping(source = "clientRequest.password", target = "password")
    @Mapping(source = "clientRequest.status", target = "status")
    Client dtoToEntity(ClientRequest clientRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClientRequest dto, @MappingTarget Client entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDtoAccount(AccountRequest dto, @MappingTarget Account entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDtoTransaction(TransactionRequest dto, @MappingTarget Transactions entity);

    @Mapping(source = "accountRequest.initialBalance", target = "availableBalance")
    Account dtoToEntityAccount(AccountRequest accountRequest);

    Transactions dtoToEntityTransaction(TransactionRequest transactionRequest);

    List<ClientResponse> entityListToDto(List<Client> clientRequests);

    List<TransactionResponse> entityListToDtoTransaction(List<Transactions> transactions);

    List<AccountResponse> entityListToDtoAccount(List<Account> accounts);


}
