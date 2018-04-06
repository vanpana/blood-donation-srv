package com.cyberschnitzel.Domain.Transport.Requests;

class MessageRequest {
    private String email;
    private String password;
    private String token;
    private String message;

    public MessageRequest(String email, String password, String token, String message) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
