package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.CredentialsEntity;
import com.cyberschnitzel.Domain.Entities.Doctor;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Entities.Personnel;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;

class InputValidator {
    private static void validateInput(MessageRequest messageRequest, CredentialsEntity.EntityType entityType) throws HandlingException {
        // Check if the entity exists and verify the credentials
        try {
            if (messageRequest.getToken() == null) {
                Controller.checkCredentialsNoToken(messageRequest.getEmail(), messageRequest.getPassword(), entityType);
                return;
            }
            Controller.checkCredentials(messageRequest.getEmail(), messageRequest.getPassword(), messageRequest.getToken(),
                    entityType);
        } catch (ControllerException ce) {
            throw new HandlingException(ce.getMessage());
        }

        // TODO: Other checks (app version, if the user is banned etc)
    }

    static Donator validateDonatorInput(MessageRequest messageRequest) throws HandlingException {
        validateInput(messageRequest, CredentialsEntity.EntityType.DONATOR);
        return Controller.getDonatorByEmail(messageRequest.getEmail());
    }

    static Personnel validatePersonnelInput(MessageRequest messageRequest) throws HandlingException {
        validateInput(messageRequest, CredentialsEntity.EntityType.PERSONNEL);
        return Controller.getPersonnelByEmail(messageRequest.getEmail());
    }
}
