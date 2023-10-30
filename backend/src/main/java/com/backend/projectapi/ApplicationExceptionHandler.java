package com.backend.projectapi;

import com.backend.projectapi.exception.ErrorDataResponse;
import com.backend.projectapi.exception.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException exc){
        ErrorDataResponse response = new ErrorDataResponse(Collections.singletonList(exc.getLocalizedMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorList = new ArrayList<>();
        for(ObjectError err : ex.getBindingResult().getAllErrors()){
            errorList.add(err.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorDataResponse(errorList), HttpStatus.BAD_REQUEST);
    }
}
