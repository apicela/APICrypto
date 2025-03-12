package com.apicela.apicrypto.models.responses;

public class DefaultApiResponse<T> {
    private String message;
    private T data;
    private int statusCode;

    public DefaultApiResponse(String message, T data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public DefaultApiResponse(String message, int statusCode) {
        this(message, null, statusCode);
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
