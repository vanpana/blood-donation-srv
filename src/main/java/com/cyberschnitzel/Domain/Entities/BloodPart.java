package com.cyberschnitzel.Domain.Entities;

import java.util.Date;

public class BloodPart extends Blood {

	private Integer idBlood;
	private Date expirationDate;
	public BloodPart(String bloodType) {
		super(bloodType);
	}
	public BloodPart(Integer _id, Integer _idblood, Date _exp)
	{

		setId(_id);
		idBlood =_idblood;
		expirationDate = _exp;

	}

	public Integer getIdBlood() {
		return idBlood;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
