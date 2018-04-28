package com.cyberschnitzel.Domain.Transport.Responses;

import com.cyberschnitzel.Domain.Entities.BloodType;
import com.cyberschnitzel.Domain.Entities.Donation;

import java.util.Date;

public class DonationsResponse {
	private Integer id;
	private String cnp;
	private double quantity;
	private Donation.DonationStatus status;
	private BloodType bloodType;
	private String donatorName;
	private String submittedDate;
	private String location;

	public DonationsResponse(Integer id, String cnp, double quantity, Donation.DonationStatus status, BloodType bloodType, String donatorName, String submittedDate, String location) {
		this.id = id;
		this.cnp = cnp;
		this.quantity = quantity;
		this.status = status;
		this.bloodType = bloodType;
		this.donatorName = donatorName;
		this.submittedDate = submittedDate;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Donation.DonationStatus getStatus() {
		return status;
	}

	public void setStatus(Donation.DonationStatus status) {
		this.status = status;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



}
