package com.devsu.querydatamanagement.infraestructure.kafkaProducer.dto;

public record TransactionExtractEvent(

        String createDate,

        String transactionType,

        String transactionAmount,

        String balance,

        String initialBalance,

        String accountNumber,

        boolean status,

        String clientName
) {
}
