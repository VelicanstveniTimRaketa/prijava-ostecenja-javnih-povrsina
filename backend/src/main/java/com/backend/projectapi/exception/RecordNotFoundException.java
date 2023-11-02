package com.backend.projectapi.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String msg) {
        super(msg);
    }
}
