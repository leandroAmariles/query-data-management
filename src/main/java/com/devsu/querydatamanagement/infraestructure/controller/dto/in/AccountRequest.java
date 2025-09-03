package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import java.math.BigDecimal;

public record AccountRequest (

        String accountId,
        
         String accountNumber,

         String accountType,

         BigDecimal initialBalance,

         Long personId
){
}
