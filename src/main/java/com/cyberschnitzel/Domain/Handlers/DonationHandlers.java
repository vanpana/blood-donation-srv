package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Donation;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddDonationRequest;
import com.google.gson.Gson;

import javax.ws.rs.core.Response;

public class DonationHandlers {
    /**
     * Checks the donator and adds a donation
     *
     * @param input - Should be AddDonationRequest
     * @return the donation object created
     */
    // TODO: In this case, donator should be changed to personnel
    public static Donation addDonation(String input) throws HandlingException {
        try {
            // Try to construct the add donation request
            AddDonationRequest addDonationRequest = new Gson().fromJson(input, AddDonationRequest.class);

            // Validate input and get donor
            Donator donator = DonatorInputValidator.validateInput(addDonationRequest);

            // Try to add the donation
            int donationID = Controller.addDonation(donator.getCnp(), addDonationRequest.getQuantity(), addDonationRequest.getStatus(), addDonationRequest.getBloodID());

            // Throw exception if donation couldn't be added
            if (donationID == -1) throw new Exception("Donation wasn't added, ID = -1!");

            // Try to return the donation by the id it was created
            return Controller.getDonationByID(donationID);
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle add donation: " + ex.getMessage());
        }
    }
}
