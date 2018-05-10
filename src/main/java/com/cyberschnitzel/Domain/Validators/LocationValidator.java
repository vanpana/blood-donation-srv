package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Location;

public class LocationValidator implements Validator<Location> {
	@Override
	public boolean validate(Location entity) {
		return true;
	}
}
