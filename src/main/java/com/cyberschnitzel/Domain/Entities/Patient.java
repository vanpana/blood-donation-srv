package com.cyberschnitzel.Domain.Entities;

public class Patient extends Entity{
    private String cnp;
    private String name;

    public Patient(String cnp, String name) {
        this.cnp = cnp;
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
