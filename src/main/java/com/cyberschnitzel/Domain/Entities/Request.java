package com.cyberschnitzel.Domain.Entities;

public class Request extends Entity {
    private float quantity;
    private int urgency;
    private String bloodPartType;
    private int locationId;
    private BloodType bloodType;

    public Request() {
    }

    public Request(float quantity, int urgency, BloodType bloodType, int locationId, String bloodPartType, Integer id) {
        this.setId(id);
        this.quantity = quantity;
        this.urgency = urgency;
        this.bloodType = bloodType;
        this.locationId = locationId;
        this.bloodPartType = bloodPartType;
    }

    public Request(int quantity, int urgency, String bloodPartType, BloodType bloodType, int locationId) {
        this.quantity = quantity;
        this.urgency = urgency;
        this.bloodPartType = bloodPartType;
        this.bloodType = bloodType;
        this.locationId = locationId;
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

    @Override
    public String toString() {
        return "Request{" +
                "quantity=" + quantity +
                ", urgency=" + urgency +
                ", bloodPartType='" + bloodPartType + '\'' +
                ", bloodType=" + bloodType +
                ", locationId=" + locationId +
                '}';
    }
}
