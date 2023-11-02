package com.backend.projectapi;


public class ResponseData<T> {
    private boolean success;
    private T data;
    private String error;

    public ResponseData(boolean success, T data, String error){
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(true, data, null);
    }

    public static <T> ResponseData<T> error(String error) {
        return new ResponseData<>(false, null, error);
    }
}
