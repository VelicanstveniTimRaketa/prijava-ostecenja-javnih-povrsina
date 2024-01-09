package com.backend.projectapi.controller;

import com.backend.projectapi.exception.RecordNotFoundException;
import com.backend.projectapi.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RecordNotFoundException handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new RecordNotFoundException(""+
                HttpStatus.FORBIDDEN.value()+
                new Date()+
                ex.getMessage()+
                request.getDescription(false));
    }
}
