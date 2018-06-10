package com.cyberschnitzel.Domain.Entities;

import java.util.Date;

public class BloodPart extends Blood {

	private Integer idBlood;
	private Date expirationDate;

	public Float getQuantity() {
		return quantity;
	}

	private Float quantity;
	public BloodPart(String bloodType) {
		super(bloodType);
	}

	public void setIdBlood(Integer idBlood) {
		this.idBlood = idBlood;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public BloodPart(Integer _id, Integer _idblood, Date _exp)
	{

		setId(_id);
		idBlood =_idblood;
		expirationDate = _exp;

	}

	public BloodPart(Integer _idblood, Date _exp)
	{
		idBlood =_idblood;
		expirationDate = _exp;
	}

	public BloodPart(Integer _idblood, Date _exp, Float _quantity)
	{
		idBlood =_idblood;
		expirationDate = _exp;
		quantity = _quantity;
	}

	public Integer getIdBlood() {
		return idBlood;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
