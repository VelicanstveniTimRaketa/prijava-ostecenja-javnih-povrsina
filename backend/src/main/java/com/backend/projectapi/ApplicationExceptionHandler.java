package com.backend.projectapi;

import com.backend.projectapi.exception.ErrorDataResponse;
import com.backend.projectapi.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException exc){
        ErrorDataResponse response = new ErrorDataResponse(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
