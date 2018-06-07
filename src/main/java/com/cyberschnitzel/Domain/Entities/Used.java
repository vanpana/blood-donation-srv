package com.cyberschnitzel.Domain.Entities;

public class Used  extends Entity{
    private Integer idDonation;
    private Integer requestId;
    private float quantity;
    private String bloodPartType;

    public Used(Integer idDonation, Integer requestId, float quantity, String bloodPartType) {
        this.idDonation = idDonation;
        this.requestId = requestId;
        this.quantity = quantity;
        this.bloodPartType = bloodPartType;
    }

    public Integer getIdDonation() {
        return idDonation;
    }

    public void setIdDonation(Integer idDonation) {
        this.idDonation = idDonation;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getBloodPartType() {
        return bloodPartType;
    }

    public void setBloodPartType(String bloodPartType) {
        this.bloodPartType = bloodPartType;
    }
}
