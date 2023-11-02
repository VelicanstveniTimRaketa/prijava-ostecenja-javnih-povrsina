package com.backend.projectapi;


import java.util.List;

public class ResponseData<T> {
    private boolean success;
    private T data;
    private List<String> errors;

    public ResponseData(boolean success, T data, List<String> errors){
        this.success = success;
        this.data = data;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public T getData(){
        return this.data;
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(true, data, null);
    }

    public static <T> ResponseData<T> error(List<String> errors) {
        return new ResponseData<>(false, null, errors);
    }
}
