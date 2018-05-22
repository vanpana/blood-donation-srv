package com.cyberschnitzel.Domain.Transport.Responses;

import com.cyberschnitzel.Domain.Entities.BloodType;

public class RequestResponse {
	private float quantity;
	private int urgency;
	private String bloodPartType;
	private String location;
	private int id;

	public int getId() {
		return id;
	}

	public RequestResponse(float quantity, int urgency, String bloodPartType, String location, int id, BloodType bloodType) {
		this.quantity = quantity;
		this.urgency = urgency;
		this.bloodPartType = bloodPartType;
		this.location = location;
		this.id = id;
		this.bloodType = bloodType;
	}

	public void setId(int id) {
		this.id = id;

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

	public String getBloodPartType() {
		return bloodPartType;
	}

	public void setBloodPartType(String bloodPartType) {
		this.bloodPartType = bloodPartType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	private BloodType bloodType;
}
