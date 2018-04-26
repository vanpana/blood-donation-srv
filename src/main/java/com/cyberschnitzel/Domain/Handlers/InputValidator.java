package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.CredentialsEntity;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Entities.Personnel;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;

class InputValidator {
    private static void validateInput(MessageRequest messageRequest, boolean isDonator) throws HandlingException {
        // Check if the entity exists and verify the credentials
        try {
            Controller.checkCredentials(messageRequest.getEmail(), messageRequest.getPassword(), messageRequest.getToken(),
                    isDonator);
        } catch (ControllerException ce) {
            throw new HandlingException(ce.getMessage());
        }

        // TODO: Other checks (app version, if the user is banned etc)
    }

    static Donator validateDonatorInput(MessageRequest messageRequest) throws HandlingException {
        validateInput(messageRequest, true);
        return Controller.getDonatorByEmail(messageRequest.getEmail());
    }

    static Personnel validatePersonnelInput(MessageRequest messageRequest) throws HandlingException {
        validateInput(messageRequest, false);
        return Controller.getPersonnelByEmail(messageRequest.getEmail());
    }
}
