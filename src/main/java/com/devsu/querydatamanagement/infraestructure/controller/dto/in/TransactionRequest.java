package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest (

         Long idTransaction,

         String transactionType,

         BigDecimal amount,
        
         Long accountId,

         Long personId
){
}
