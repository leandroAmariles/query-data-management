package com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UpdateInfoCustomerDto(


        Long personId,
        String name,
        String gender,
        int age,
        String identification,
        String address,
        String phone,
        String clientId,
        String password,
        boolean status,
        List<Long> accounts

) {
}
