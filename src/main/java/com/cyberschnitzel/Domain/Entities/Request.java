package com.cyberschnitzel.Domain.Entities;

public class Request extends Entity {
    private float quantity;
    private int urgency;
    private String bloodPartType;
    private int locationId;
    private BloodType bloodType;
    private int doctorId;
    private int status;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Request() {
    }



    public Request(float quantity, int urgency, BloodType bloodType, int locationId, String bloodPartType, int doctorId) {
        this.quantity = quantity;
        this.urgency = urgency;
        this.bloodPartType = bloodPartType;
        this.bloodType = bloodType;
        this.locationId = locationId;
        this.doctorId = doctorId;
    }

    public String getBloodPartType() {
        return bloodPartType;
    }

    public void setBloodPartType(String bloodPartType) {
        this.bloodPartType = bloodPartType;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public int getLocation() {
        return locationId;
    }

    public void setLocation(int location) {
        this.locationId = location;
    }

    public int getStatus() {
        return status;
    }

    public Request setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "quantity=" + quantity +
                ", urgency=" + urgency +
                ", bloodPartType='" + bloodPartType + '\'' +
                ", locationId=" + locationId +
                ", bloodType=" + bloodType +
                ", doctorId=" + doctorId +
                '}';
    }
}
