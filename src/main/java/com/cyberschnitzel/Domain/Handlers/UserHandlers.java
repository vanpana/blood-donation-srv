package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Entities.UserInfo;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Exceptions.HashingException;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Util.Hasher;
import com.google.gson.Gson;

public class UserHandlers {
    public static Donator checkDonatorLogin(String input) throws HandlingException {
        MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

        InputValidator.validateDonatorInput(messageRequest);

        Donator don = Controller.getDonatorByEmail(messageRequest.getEmail());
        if (don == null)
            throw new HandlingException("no donator with that email");
        try {
            String tkn = Hasher.getToken();
            Controller.updateDonatorToken(don.getId(), tkn);
            don.setToken(tkn);
            return don;
        } catch (ControllerException | HashingException e) {
            throw new HandlingException(e.getMessage());
        }
    }

    public static int createDonator(String input) throws HandlingException {
        MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
        UserInfo userInfo = new Gson().fromJson(messageRequest.getMessage(), UserInfo.class);

        try {
            return Controller.addDonator(userInfo.getCNP(), messageRequest.getEmail(), userInfo.getName(),
                    userInfo.getBloodType(), messageRequest.getPassword(), "", userInfo.getLocation());
        } catch (ControllerException e) {
            throw new HandlingException(e.getMessage());
        }
    }

    public static Donator getDonatorByCnp(String input) throws HandlingException {
        MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

        InputValidator.validatePersonnelInput(messageRequest);

        Donator donator = Controller.getDonatorByCnp(messageRequest.getMessage());

        if (donator != null) {
            return donator;
        } else
            throw new HandlingException("no donator with that cnp");

    }


}
