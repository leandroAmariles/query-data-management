package com.devsu.querydatamanagement.domain.exceptions;

public class IllegalTransactionAmount extends RuntimeException {
    public IllegalTransactionAmount(String message) {
        super(message);
    }
}
