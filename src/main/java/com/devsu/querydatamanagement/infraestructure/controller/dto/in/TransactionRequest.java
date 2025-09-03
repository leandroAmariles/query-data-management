package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import java.math.BigDecimal;

public record TransactionRequest (

         Long idTransaction,

         String transactionType,

         BigDecimal amount,
        
         Long accountId,

         Long personId
){
}
