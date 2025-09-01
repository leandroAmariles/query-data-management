package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto;

public record TransactionRequest (

         Long idTransaction,

         String transactionType,

         String amount,
        
         Long idAccount,

         Long idClient
){
}
