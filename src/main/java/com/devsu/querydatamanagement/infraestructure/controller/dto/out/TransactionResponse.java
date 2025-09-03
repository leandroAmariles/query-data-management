package com.devsu.querydatamanagement.infraestructure.controller.dto.out;


public record TransactionResponse (
        
         Long transactionId,

         String transactionDate,

         String transactionType,

         String amount,

         String balance,

         String accountId,

         String personId

){
}
