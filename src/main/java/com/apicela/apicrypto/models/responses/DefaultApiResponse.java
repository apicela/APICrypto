package com.apicela.apicrypto.models.responses;

public class DefaultApiResponse<T> {
    private String message;
    private T data;
    private int status;

    public DefaultApiResponse(String message, T data, int statusCode) {
        this.message = message;
        this.data = data;
        this.status = statusCode;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DefaultApiResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
