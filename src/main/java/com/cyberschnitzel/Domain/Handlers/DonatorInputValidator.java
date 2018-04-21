package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;

public class DonatorInputValidator {
    public static Donator validateInput(MessageRequest messageRequest) throws HandlingException {
        // Get donator from the messageRequest
        Donator donator = Controller.getDonatorByEmail(messageRequest.getEmail());

        // If donator isn't found, throw an exception
        if (donator == null) throw new HandlingException(String.format("Donator with email %s does not exist!",
                messageRequest.getEmail()));

        // TODO: Check credentials

        // TODO: Other checks (app version, if the user is banned etc)

        return donator;
    }
}
