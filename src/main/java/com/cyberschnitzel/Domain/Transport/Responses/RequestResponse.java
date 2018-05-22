package com.cyberschnitzel.Domain.Transport.Responses;

import com.cyberschnitzel.Domain.Entities.BloodType;

public class RequestResponse {
	private float quantity;
	private int urgency;
	private String bloodPartType;
	private String locationName;

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

	public String getBloodPartType() {
		return bloodPartType;
	}

	public void setBloodPartType(String bloodPartType) {
		this.bloodPartType = bloodPartType;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public RequestResponse(float quantity, int urgency, String bloodPartType, String locationName, BloodType bloodType) {
		this.quantity = quantity;
		this.urgency = urgency;

		this.bloodPartType = bloodPartType;
		this.locationName = locationName;
		this.bloodType = bloodType;
	}

	private BloodType bloodType;
}
