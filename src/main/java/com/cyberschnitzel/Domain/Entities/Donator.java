package com.cyberschnitzel.Domain.Entities;

public class Donator extends Entity {
    private String cnp;
    private String name;
    private BloodType bloodtype;
    private String email;
    private String password;
    private String token;

    public Donator(String cnp, String email, String name) {
        this.cnp = cnp;
        this.email = email;
        this.name = name;

        this.password = "";
        this.token = "";
    }

    public Donator setBloodType(String bloodType) {
        if (bloodType != null) this.bloodtype = BloodType.getByString(bloodType);
        return this;
    }

    public Donator setPassword(String password) {
        if (password != null) this.password = password;
        return this;
    }

    public Donator setToken(String token) {
        if (token != null) this.token = token;
        return this;
    }

    public String getCnp() {
        return cnp;
    }

    public String getName() {
        return name;
    }

    public BloodType getBloodtype() {
        return bloodtype;
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
}
