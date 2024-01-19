package com.backend.projectapi.config;

import com.backend.projectapi.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RecordNotFoundException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
