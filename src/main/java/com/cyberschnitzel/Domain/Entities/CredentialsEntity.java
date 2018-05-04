package com.cyberschnitzel.Domain.Entities;

public class CredentialsEntity extends Entity {
    public enum EntityType{
        PERSONNEL, DONATOR, DOCTOR
    }


    protected String email, password, token;

    public CredentialsEntity(String email) {
        this.email = email;
    }

    public CredentialsEntity(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
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

    public CredentialsEntity setPassword(String password) {
        if (password != null) this.password = password;
        return this;
    }

    public CredentialsEntity setToken(String token) {
        if (token != null) this.token = token;
        return this;
    }
}
