package com.cyberschnitzel.Domain.Transport.Requests;

public class UpdateDonationStatusRequest extends MessageRequest{
	int donationID;
	Integer statusID;

	public Integer getStatusID() {
		return statusID;
	}

	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}

	public UpdateDonationStatusRequest(String email, String password, String token, Integer statusID, int donationID) {
		super(email, password, token);
		this.donationID = donationID;
		this.statusID = statusID;
	}

	public int getDonationID() {
		return donationID;
	}

	public void setDonationID(int donationID) {
		this.donationID = donationID;
	}
}
