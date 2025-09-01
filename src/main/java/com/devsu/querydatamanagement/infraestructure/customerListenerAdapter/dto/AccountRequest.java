package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto;

import java.math.BigDecimal;

public record AccountRequest (

        String accountId,
        
         String accountNumber,

         String accountType,

         BigDecimal initialBalance,

         Long clientId
){
}
