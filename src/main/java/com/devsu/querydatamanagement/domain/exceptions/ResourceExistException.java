package com.devsu.querydatamanagement.domain.exceptions;

public class ResourceExistException extends RuntimeException {

    public ResourceExistException(String message) {
        super(message);
    }
}
