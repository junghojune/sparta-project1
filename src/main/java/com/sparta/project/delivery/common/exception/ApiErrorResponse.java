package com.sparta.project.delivery.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class ApiErrorResponse {

    private int status;
    private String errorCode;
    private String message;
    private Map<String, String> validation;

    public void addValidation(String field, String errorMessage) {
        if (validation == null) {
            validation = new HashMap<>();
        }
        this.validation.put(field, errorMessage);
    }
}
