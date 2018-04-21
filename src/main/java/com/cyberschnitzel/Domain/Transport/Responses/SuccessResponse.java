package com.cyberschnitzel.Domain.Transport.Responses;

public class SuccessResponse {
    private boolean success;
    private String message;

    public SuccessResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String isMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
