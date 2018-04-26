package com.cyberschnitzel.Domain.Entities;

import java.util.Date;

public class BloodPart extends Blood {

	private Integer idBlood;
	private Date exp;
	public BloodPart(String bloodType) {
		super(bloodType);
	}
	public BloodPart(Integer _id, Integer _idblood, Date _exp)
	{

		setId(_id);
		idBlood =_idblood;
		exp = _exp;

	}

	public Integer getIdBlood() {
		return idBlood;
	}

	public Date getExp() {
		return exp;
	}
}
