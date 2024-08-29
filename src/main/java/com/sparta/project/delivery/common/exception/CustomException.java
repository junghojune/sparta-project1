package com.sparta.project.delivery.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final DeliveryError error;

    public CustomException(DeliveryError error){
        this.error = error;
    }
}
