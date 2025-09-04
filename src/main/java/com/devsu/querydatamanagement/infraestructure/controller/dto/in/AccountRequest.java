package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequest (

        String accountId,
        
         String accountNumber,

         String accountType,

         BigDecimal initialBalance,

         Long personId
){
}
