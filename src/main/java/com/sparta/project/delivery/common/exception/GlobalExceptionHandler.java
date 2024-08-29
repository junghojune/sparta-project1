package com.sparta.project.delivery.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception e){
        log.error(">>예외 ",e);

        var response = ApiErrorResponse.builder()
                .errorCode("500")
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(500).body(response);
    }

    //Request Validation 에 의한 에러 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiErrorResponse ExceptionHandler(MethodArgumentNotValidException e) {

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errorCode("BAD_REQUEST").message("잘못된 요청")
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorResponse;
    }

    //Custom Error
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> customException(CustomException e){
        int statusCode = e.getError().getStatusCode();
        ApiErrorResponse response = ApiErrorResponse.builder()
                .statusCode(statusCode)
                .errorCode(e.getError().getErrorCode())
                .message(e.getError().getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }
}
