package com.cyberschnitzel.Domain.Transport.Requests;

public class UpdateDonationRequest extends AddDonationRequest {
    int donationID;

    public UpdateDonationRequest(String email, String password, String token, double quantity, int bloodID, int donationID) {
        super(email, password, token, quantity, bloodID);
        this.donationID = donationID;
    }

    public int getDonationID() {
        return donationID;
    }

    public void setDonationID(int donationID) {
        this.donationID = donationID;
    }
}
