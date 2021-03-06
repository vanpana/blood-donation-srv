package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.BloodPart;
import com.cyberschnitzel.Domain.Entities.Request;
import com.cyberschnitzel.Domain.Entities.Used;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Domain.Transport.Responses.AvailableBloodResponse;
import com.cyberschnitzel.Domain.Transport.Responses.RequestResponse;
import com.cyberschnitzel.Domain.Transport.Responses.SuccessResponse;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public class RequestsHandlers {
	public static List<RequestResponse> getAllRequests(String input) throws HandlingException {

		MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
		// Validate input
		InputValidator.validatePersonnelInput(messageRequest);
		List<Request> requests = Controller.getAllRequests();
		return requests.stream().map(req -> new RequestResponse(req.getQuantity(),req.getUrgency(),req.getBloodPartType(),
				Controller.getLocationById(req.getLocation()).getName(),req.getId(),req.getBloodType(), req.getDoctorId())).collect(Collectors.toList());
	}

	public static List<AvailableBloodResponse> getAllAvailableBloodForRequest(String input) throws HandlingException {
		MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
		// Validate input
		InputValidator.validatePersonnelInput(messageRequest);
		Integer idRequest = new Gson().fromJson(messageRequest.getMessage(), Integer.class);
		return Controller.getAllAvailableBloodForRequest(idRequest);
	}


	public static Integer useBlood(String useBloodRequestJson) throws HandlingException, ControllerException {
		MessageRequest messageRequest = new Gson().fromJson(useBloodRequestJson, MessageRequest.class);
		// Validate input
		InputValidator.validatePersonnelInput(messageRequest);

		Used u = new Gson().fromJson(messageRequest.getMessage(), Used.class);

		return Controller.addUsed(u.getIdDonation(),u.getRequestId(),u.getQuantity(), u.getBloodPartType());
	}
}
