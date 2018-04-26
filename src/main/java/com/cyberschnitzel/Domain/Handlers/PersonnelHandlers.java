package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Personnel;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Exceptions.HashingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Util.Hasher;
import com.google.gson.Gson;

public class PersonnelHandlers {

    public static Personnel addPersonnel(String input) throws HandlingException{
        return null;

    }

    /**
     * Checks the personnel
     *
     * @param input - Should be AddPersonnelRequest
     * @return the personnel object created
     */

    public static Personnel updatePersonnel(String input) throws HandlingException {
        return null;
    }

    public static Personnel checkPersonnelLogin(String input) throws HandlingException {
		MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

		InputValidator.validatePersonnelInput(messageRequest);

		Personnel pers = Controller.getPersonnelByEmail(messageRequest.getEmail());
		try {
			String tkn =  Hasher.getToken();
			Controller.updatePersonnelToken(pers.getId(), Hasher.getToken());
			pers.setToken(tkn);
			return pers;
		} catch (ControllerException | HashingException e) {
			throw new HandlingException(e.getMessage());
		}

	}
}
