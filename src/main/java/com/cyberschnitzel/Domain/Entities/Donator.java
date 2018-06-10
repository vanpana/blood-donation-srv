package com.cyberschnitzel.Domain.Entities;

public class Donator extends CredentialsEntity {
    private String cnp;
    private String name;
    private BloodType bloodtype;
    private String location;

    public Donator() {}

    public void setBloodtype(BloodType bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public Donator(String cnp, String email, String name, String location) {
        super(email);
        this.cnp = cnp;
        this.name = name;
        this.location = location;
    }

    public Donator(String email, String password, String token, String cnp, String name, String location) {
        super(email, password, token);
        this.cnp = cnp;
        this.name = name;
        this.location = location;
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

    public Donator setCnp(String cnp) {
        if (cnp != null) this.cnp = cnp;
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
