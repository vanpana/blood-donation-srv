package com.cyberschnitzel.Domain.Entities;

public class Personnel extends CredentialsEntity {

    private String name;

    public Personnel(String name, String email){
        super(email);
        this.name=name;
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

    public Personnel setToken(String token) {
        if (token != null) this.token = token;
        return this;
    }
}

