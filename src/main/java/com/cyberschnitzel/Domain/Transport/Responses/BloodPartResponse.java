package com.cyberschnitzel.Domain.Transport.Responses;

import com.cyberschnitzel.Domain.Entities.BloodPart;
import com.cyberschnitzel.Domain.Entities.BloodType;

public class BloodPartResponse {
	private Integer id;
	private Integer idBlood;
	private String expirationDate;
	//bloodPartType, BloodType, receivedDate
	private String bloodPartType;

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	private BloodType bloodType;
	private String receivedDate;
	private String status;
	private Float quantity;

	public BloodPartResponse(BloodPart bloodPart, String bloodPartType, BloodType bloodType, String receivedDate, Float quantity) {
		this.id = bloodPart.getId();

		this.idBlood = bloodPart.getIdBlood();
		this.bloodPartType = bloodPartType;
		this.expirationDate = bloodPart.getExpirationDate().toString();
		this.bloodType = bloodType;
		this.receivedDate = receivedDate;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdBlood() {
		return idBlood;
	}

	public void setIdBlood(Integer idBlood) {
		this.idBlood = idBlood;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getBloodPartType() {
		return bloodPartType;
	}

	public void setBloodPartType(String bloodPartType) {
		this.bloodPartType = bloodPartType;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
}
