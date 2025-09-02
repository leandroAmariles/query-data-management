package com.devsu.querydatamanagement.infraestructure.controller.dto.in;

import lombok.Builder;

@Builder
public record EventResponse (

        boolean success,

        String message

) {
}
