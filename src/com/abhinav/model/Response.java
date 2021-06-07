package com.abhinav.model;
/**
 * A Response class to Return Messages along with Status to mimic an API response
 * */
public class Response {
    private boolean status;
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
