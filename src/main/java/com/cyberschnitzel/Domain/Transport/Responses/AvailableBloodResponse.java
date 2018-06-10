package com.cyberschnitzel.Domain.Transport.Responses;

public class AvailableBloodResponse {
	int id;
	float quantity;

	public int getId() {
		return id;
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

	public AvailableBloodResponse(int id, float quantity) {
		this.id = id;
		this.quantity = quantity;
	}
}
