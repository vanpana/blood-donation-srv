package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddBloodPartRequest;
import com.google.gson.Gson;

public class BloodPartHandlers {
	public static Integer addBloodPart(String input) throws HandlingException {
		try {
			// Try to construct the add donation request
			AddBloodPartRequest addBloodPartRequest = new Gson().fromJson(input, AddBloodPartRequest.class);

			// Validate input and get donor
			Donator donator = DonatorInputValidator.validateInput(addBloodPartRequest);

			int retCode = Controller.addBloodPart(addBloodPartRequest.getPartClass(), addBloodPartRequest.getBloodId(),
					addBloodPartRequest.getPartId(), addBloodPartRequest.getExpDate());
			// Throw exception if donation couldn't be added
			if (retCode != 0) throw new Exception("Error inserting part");

			// Try to return the donation by the id it was created
			return retCode;
		} catch (Exception ex) {
			throw new HandlingException("Failed to handle add part: " + ex.getMessage());
		}
	}
}
