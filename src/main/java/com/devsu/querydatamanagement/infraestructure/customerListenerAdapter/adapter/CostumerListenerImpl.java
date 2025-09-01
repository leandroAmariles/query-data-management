package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.adapter;

import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.CustomerListener;
import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.ClientRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CostumerListenerImpl implements CustomerListener {

    private final CommandWriteService commandWriteService;


    @Override
    @KafkaListener(topics = "customer-topic-create" , groupId = "${spring.kafka.consumer.group-id}")
    public void clientEvent(String eventJson) {
        ClientRequest clientRequest = new Gson().fromJson(eventJson, ClientRequest.class);
        commandWriteService.SaveCustomer(clientRequest);
    }
}
