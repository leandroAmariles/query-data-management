package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto;

import lombok.Builder;

@Builder
public record EventResponse (

        boolean success,

        String message

) {
}
