package com.cyberschnitzel.Domain.Entities;

public class Used  extends Entity{
    private Integer idDonation;
    private String patientCNP;
    private float quantity;

    public Used(Integer idDonation, String patientCNP, float quantity) {
        this.idDonation = idDonation;
        this.patientCNP = patientCNP;
        this.quantity = quantity;
    }

    public Integer getIdDonation() {
        return idDonation;
    }

    public void setIdDonation(Integer idDonation) {
        this.idDonation = idDonation;
    }

    public String getPatientCNP() {
        return patientCNP;
    }

    public void setPatientCNP(String patientCNP) {
        this.patientCNP = patientCNP;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
