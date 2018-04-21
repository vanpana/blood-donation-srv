package com.cyberschnitzel.Domain.Transport.Requests;

public class AddDonationRequest extends MessageRequest {
    private double quantity;
    private int status;
    private int bloodID;

    public AddDonationRequest(String email, String password, String token, double quantity, int bloodID) {
        super(email, password, token);
        this.quantity = quantity;
        this.bloodID = bloodID;
    }

    public AddDonationRequest setStatus(int status) {
        this.status = status;
        return this;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public int getBloodID() {
        return bloodID;
    }

    public void setBloodID(int bloodID) {
        this.bloodID = bloodID;
    }
}
