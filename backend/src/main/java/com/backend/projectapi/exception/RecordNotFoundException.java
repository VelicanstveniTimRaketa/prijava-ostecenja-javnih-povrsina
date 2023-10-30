package com.backend.projectapi.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String id) {
        super("The id " + id + " does not exist in our records");
    }
}
