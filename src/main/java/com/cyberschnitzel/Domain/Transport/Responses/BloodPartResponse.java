package com.cyberschnitzel.Domain.Transport.Responses;

import com.cyberschnitzel.Domain.Entities.BloodPart;

public class BloodPartResponse {
	private BloodPart bloodPart;
	private String type;

	public BloodPartResponse(BloodPart bloodPart, String type) {
		this.bloodPart = bloodPart;
		this.type = type;
	}

	public BloodPart getBloodPart() {
		return bloodPart;
	}

	public void setBloodPart(BloodPart bloodPart) {
		this.bloodPart = bloodPart;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
