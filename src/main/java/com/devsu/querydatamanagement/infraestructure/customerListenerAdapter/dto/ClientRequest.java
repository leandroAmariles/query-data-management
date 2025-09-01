package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto;

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
