package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import lombok.Builder;

@Builder
public record ClientRequest(

        String name,

        String gender,

        int age,

        String identification,

        String address,

        String phone,

        String clientId,

        String password,

        boolean status) {
}
