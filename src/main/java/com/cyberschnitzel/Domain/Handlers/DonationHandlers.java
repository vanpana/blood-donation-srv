package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Donation;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddDonationRequest;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Domain.Transport.Requests.UpdateDonationRequest;
import com.google.gson.Gson;

import java.util.List;

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

    /**
     * Checks the donator and updates a donation
     *
     * @param input - Should be AddDonationRequest
     * @return the donation object created
     */
    public static Donation updateDonation(String input) throws HandlingException {
        try {
            // Try to construct the update donation request
            UpdateDonationRequest updateDonationRequest = new Gson().fromJson(input, UpdateDonationRequest.class);

            // Validate input and get donor
            Donator donator = DonatorInputValidator.validateInput(updateDonationRequest);

            // Try to update the donation
            Controller.updateDonation(updateDonationRequest.getDonationID(), donator.getCnp(),
                    updateDonationRequest.getQuantity(), updateDonationRequest.getStatus(), updateDonationRequest.getBloodID());

            // Try to return the donation by the id it was updated
            return Controller.getDonationByID(updateDonationRequest.getDonationID());
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle update donation: " + ex.getMessage());
        }
    }

    /**
     * Checks the donator and deletes a donation
     *
     * @param input - Should be MessageRequest with empty message
     * @return the donation object deleted
     */
    public static Donation deleteDonation(String input, int donationID) throws HandlingException {
        try {
            // Try to construct the message request
            MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

            // Validate input
            DonatorInputValidator.validateInput(messageRequest);

            // Try to fetch the donation by ID
            Donation donation = Controller.getDonationByID(donationID);
            if (donation == null) throw new Exception("Inexistent donation!");

            // Delete the donation
            Controller.deleteDonation(donationID);

            // Return the deleted donation
            return donation;
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle delete donation: " + ex.getMessage());
        }
    }
}
