package com.cyberschnitzel.Domain.Transport.Requests;

public class AddBloodRequest extends MessageRequest {
    String bloodType;

    public AddBloodRequest(String email, String password, String token, String bloodType) {
        super(email, password, token);
        this.bloodType = bloodType;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
