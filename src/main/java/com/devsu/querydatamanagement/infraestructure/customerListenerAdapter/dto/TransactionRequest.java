package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto;

import java.math.BigDecimal;

public record TransactionRequest (

         Long idTransaction,

         String transactionType,

         BigDecimal amount,
        
         Long idAccount,

         Long idClient
){
}
