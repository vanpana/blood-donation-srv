package com.cyberschnitzel.Domain.Transport.Requests;

public class UpdateDonationRequest extends AddDonationRequest {
    int donationID;

    public UpdateDonationRequest(String email, String password, String token, String donatorCNP, double quantity,
                                 int bloodID, int donationID) {
        super(email, password, token, donatorCNP, quantity, bloodID);
        this.donationID = donationID;
    }

    public int getDonationID() {
        return donationID;
    }

    public void setDonationID(int donationID) {
        this.donationID = donationID;
    }
}
