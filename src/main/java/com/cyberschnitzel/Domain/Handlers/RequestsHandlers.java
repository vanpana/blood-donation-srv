package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Request;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.google.gson.Gson;

import java.util.List;

public class RequestsHandlers {
	public static List<Request> getAllRequests(String input) throws HandlingException {

		MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
		// Validate input
		InputValidator.validatePersonnelInput(messageRequest);
		return Controller.getAllRequests();
	}
}
