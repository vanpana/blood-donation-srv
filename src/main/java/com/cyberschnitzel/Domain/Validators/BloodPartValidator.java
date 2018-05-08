package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.BloodPart;

public class BloodPartValidator implements Validator<BloodPart> {
	@Override
	public boolean validate(BloodPart entity) {
		return entity.getIdBlood() != null;
	}
}
