package com.cyberschnitzel.Domain.Transport.Requests;

import java.util.Date;

public class ReceiveDonationRequest extends MessageRequest {
	//params cnp, 4 x quant, status auto, bloodType, name, dob, recv auto
	private String cnp;
	private Integer bloodQuantity, redCellsQuantity, thrombocitesQuantity, plasmaQuantity;
	private String bloodType;
	private String name;
	private String dob;

	public ReceiveDonationRequest(String email, String password, String token, String cnp, Integer bloodQuantity, Integer redCellsQuantity, Integer thrombocitesQuantity, Integer plasmaQuantity, String bloodType, String name, String dob) {
		super(email, password,token);
		this.cnp = cnp;
		this.bloodQuantity = bloodQuantity;
		this.redCellsQuantity = redCellsQuantity;
		this.thrombocitesQuantity = thrombocitesQuantity;
		this.plasmaQuantity = plasmaQuantity;
		this.bloodType = bloodType;
		this.name = name;
		this.dob = dob;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public Integer getBloodQuantity() {
		return bloodQuantity;
	}

	public void setBloodQuantity(Integer bloodQuantity) {
		this.bloodQuantity = bloodQuantity;
	}

	public Integer getRedCellsQuantity() {
		return redCellsQuantity;
	}

	public void setRedCellsQuantity(Integer redCellsQuantity) {
		this.redCellsQuantity = redCellsQuantity;
	}

	public Integer getThrombocitesQuantity() {
		return thrombocitesQuantity;
	}

	public void setThrombocitesQuantity(Integer thrombocitesQuantity) {
		this.thrombocitesQuantity = thrombocitesQuantity;
	}

	public Integer getPlasmaQuantity() {
		return plasmaQuantity;
	}

	public void setPlasmaQuantity(Integer plasmaQuantity) {
		this.plasmaQuantity = plasmaQuantity;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
