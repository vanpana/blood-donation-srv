package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Request;

public class RequestValidator implements Validator<Request> {
	@Override
	public boolean validate(Request entity) {
		return true;
	}
}
