package com.cyberschnitzel.Domain.Entities;

public class UserInfo {
    private String CNP;
    private String name;
    private String bloodType;

    public UserInfo() {
    }

    public UserInfo(String CNP, String name, String bloodType) {
        this.CNP = CNP;
        this.name = name;
        this.bloodType = bloodType;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
