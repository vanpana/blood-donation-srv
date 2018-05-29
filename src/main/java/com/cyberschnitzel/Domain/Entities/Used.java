package com.cyberschnitzel.Domain.Entities;

public class Used  extends Entity{
    private Integer idDonation;
    private String idPatient;
    private float quantity;
    private String bloodPartType;

    public Used(Integer idDonation, String idPatient, float quantity, String bloodPartType) {
        this.idDonation = idDonation;
        this.idPatient = idPatient;
        this.quantity = quantity;
        this.bloodPartType = bloodPartType;
    }

    public Integer getIdDonation() {
        return idDonation;
    }

    public void setIdDonation(Integer idDonation) {
        this.idDonation = idDonation;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
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
