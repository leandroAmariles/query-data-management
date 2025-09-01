package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.adapter;

import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.CustomerListener;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.EventResponse;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.TransactionRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CostumerListenerImpl implements CustomerListener {

    private final CommandWriteService commandWriteService;


    @Override
    @KafkaListener
    @SendTo("transaction-topic-response")
    public EventResponse balanceCheck(String request) {
        TransactionRequest transactionRequest = new Gson().fromJson(request, TransactionRequest.class);
        boolean check = commandWriteService.canProcessWithTransaction(transactionRequest.idAccount(), transactionRequest.amount());
        return check ? EventResponse.builder()
                .success(true)
                .message("Sufficient balance")
                .build()
                : EventResponse.builder()
                .success(false)
                .message("Insufficient balance")
                .build();
    }

    @Override
    @KafkaListener(topics = "customer-topic-create" , groupId = "${spring.kafka.consumer.group-id}")
    @SendTo("customer-topic-create-response")
    public EventResponse clientEvent(String eventJson) {
        ClientRequest clientRequest = new Gson().fromJson(eventJson, ClientRequest.class);
        try {
            ClientResponse clientResponse = commandWriteService.SaveCustomer(clientRequest);
            return EventResponse.builder()
                    .success(true)
                    .message(new Gson().toJson(clientResponse))
                    .build();
        } catch (Exception e) {
            return EventResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}
