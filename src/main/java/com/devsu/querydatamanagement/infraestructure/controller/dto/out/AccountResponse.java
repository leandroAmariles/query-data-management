package com.devsu.querydatamanagement.infraestructure.controller.dto.out;


import java.math.BigDecimal;

public record AccountResponse (

         Long accountId,


         String accountNumber,

         String accountType,

         BigDecimal initialBalance,

         BigDecimal availableBalance,

         boolean status,

         Long personId
) {
}
