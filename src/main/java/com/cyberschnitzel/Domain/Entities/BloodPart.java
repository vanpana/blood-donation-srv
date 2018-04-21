package com.cyberschnitzel.Domain.Entities;

import java.util.Date;

public class BloodPart extends Blood {

	Integer idBlood;
	Date exp;
	public BloodPart(String bloodType) {
		super(bloodType);
	}
	public BloodPart(Integer _id, Integer _idblood, Date _exp)
	{
		setId(_id);
		idBlood =_idblood;
		exp = _exp;

	}

}
