package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.*;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddBloodPartRequest;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Domain.Transport.Responses.BloodPartResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BloodPartHandlers {
	public static Integer addBloodPart(String input) throws HandlingException {
		try {
			// Try to construct the add donation request
			AddBloodPartRequest addBloodPartRequest = new Gson().fromJson(input, AddBloodPartRequest.class);

			// Validate input and get donor
			InputValidator.validatePersonnelInput(addBloodPartRequest);

			int retCode = Controller.addBloodPart(addBloodPartRequest.getPartClass(), addBloodPartRequest.getBloodId(),
					addBloodPartRequest.getExpDate(), addBloodPartRequest.getQuantity());
			// Throw exception if donation couldn't be added
			if (retCode != 0) throw new Exception("Error inserting part");

			// Try to return the donation by the id it was created
			return retCode;
		} catch (Exception ex) {
			throw new HandlingException("Failed to handle add part: " + ex.getMessage());
		}
	}

	public static Integer updateBloodPart(String input, Integer id) throws HandlingException {
		try {

			AddBloodPartRequest addBloodPartRequest = new Gson().fromJson(input, AddBloodPartRequest.class);

			InputValidator.validatePersonnelInput(addBloodPartRequest);

			int retCode = Controller.updateBloodPart(addBloodPartRequest.getPartClass().getSimpleName(), addBloodPartRequest.getBloodId(),
					addBloodPartRequest.getPartId(), addBloodPartRequest.getExpDate());
			// Throw exception if donation couldn't be added
			if (retCode != 0) throw new Exception("Error updating part");

			// Try to return the donation by the id it was created
			return retCode;
		} catch (Exception ex) {
			throw new HandlingException("Failed to handle update part: " + ex.getMessage());
		}
	}

	public static List<BloodPartResponse> getAllBloodParts(String input) throws HandlingException {
		MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
		// Validate input
		InputValidator.validatePersonnelInput(messageRequest);
		List<BloodPart> plasma = new ArrayList<>();
		List<BloodPart> redcells = new ArrayList<>();
		List<BloodPart> thrombocites = new ArrayList<>();
		List<BloodPartResponse> response = new ArrayList<>();

		try {
			plasma = Controller.getBloodPart("Plasma");
			redcells = Controller.getBloodPart("RedCells");
			thrombocites = Controller.getBloodPart("Thrombocites");
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		for(BloodPart b : plasma)
		{
			Blood blood = Controller.getBloodByID(b.getIdBlood());
			response.add(new BloodPartResponse(b, "Plasma", blood.getBloodType(), blood.getReceivedDate().toString(), b.getQuantity()));
		}

		for(BloodPart b : redcells)
		{
			Blood blood = Controller.getBloodByID(b.getIdBlood());
			response.add(new BloodPartResponse(b, "RedCells", blood.getBloodType(), blood.getReceivedDate().toString(), b.getQuantity()));
		}

		for(BloodPart b : thrombocites)
		{
			Blood blood = Controller.getBloodByID(b.getIdBlood());
			response.add(new BloodPartResponse(b, "Thrombocites", blood.getBloodType(), blood.getReceivedDate().toString(), b.getQuantity()));
		}


		return response;

	}



}
