package com.devsu.querydatamanagement.application.service.mapper;


import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ResponsesMapper {


    ClientResponse clientToClientResponse(Client client);
}
