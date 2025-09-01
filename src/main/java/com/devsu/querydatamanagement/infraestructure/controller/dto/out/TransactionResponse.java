package com.devsu.querydatamanagement.infraestructure.controller.dto.out;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Account;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import java.time.LocalDateTime;

public record TransactionResponse (
        
         Long transactionId,

         LocalDateTime transactionDate,

         String transactionType,

         String amount,

         String balance,

         Account account,

         Client client

){
}
