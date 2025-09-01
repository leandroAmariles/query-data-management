package com.devsu.querydatamanagement.infraestructure.kafkaProducer;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;

public interface KafkaPublisherAdapter {

    void updateCustomerInfoInReadSide(Client client);
}
