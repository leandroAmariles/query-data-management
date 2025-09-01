package com.devsu.querydatamanagement.infraestructure.kafkaProducer.adapter;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.kafkaProducer.KafkaPublisherAdapter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaPublisherAdapterImpl implements KafkaPublisherAdapter {

    private final KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void updateCustomerInfoInReadSide(Client client) {
        if (log.isTraceEnabled()) {
            log.trace("updateCustomerInfoInReadSide(client={})", client);
        }
        kafkaTemplate.send("customer-updates", new Gson().toJson(client));
    }
}
