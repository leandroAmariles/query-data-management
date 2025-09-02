package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

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
