package com.devsu.querydatamanagement.infraestructure.controller.dto.out;

import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Client;
import com.devsu.querydatamanagement.infraestructure.dbcustomeradapter.entity.Transactions;

import java.math.BigDecimal;
import java.util.List;

public record AccountResponse (

         Long accountId,


         String accountNumber,

         String accountType,

         BigDecimal initialBalance,

         BigDecimal availableBalance,

         boolean status,
        
         List<Transactions> transactions,

         Client client
) {
}
