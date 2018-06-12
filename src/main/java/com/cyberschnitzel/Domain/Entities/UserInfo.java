package com.cyberschnitzel.Domain.Entities;

public class UserInfo {
    private String CNP;
    private String name;
    private String bloodType;
    private String location;

    public UserInfo() {
    }

    public UserInfo(String CNP, String name, String bloodType, String location) {
        this.CNP = CNP;
        this.name = name;
        this.bloodType = bloodType;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
