package com.devsu.querydatamanagement.infraestructure.controller.dto.out;

public record ClientResponse (

     Long personId,

     String name,

     String gender,

     int age,

     String identification,

     String address,

     String phone,

     String clientId,

     String password,

     String status
){
    
}
