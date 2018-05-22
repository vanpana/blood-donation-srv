package com.cyberschnitzel.Domain.Entities;

public class Doctor extends CredentialsEntity {
    private String name;

    public Doctor(String email, String name) {
        super(email);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Doctor setToken(String token){
        if (token != null)
            this.token = token;
        return this;
    }

}
