package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.*;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.*;
import com.cyberschnitzel.Domain.Transport.Responses.DonationsResponse;
import com.cyberschnitzel.Domain.Transport.Responses.SuccessResponse;
import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DonationHandlers {
    /**
     * Checks the donator and adds a donation
     *
     * @param input - Should be AddDonationRequest as JSON
     * @return the donation object created
     */
    public static Donation addDonation(String input) throws HandlingException {
        try {
            // Try to construct the add donation request
            AddDonationRequest addDonationRequest = new Gson().fromJson(input, AddDonationRequest.class);

            // Validate input and get donor
            InputValidator.validatePersonnelInput(addDonationRequest);

            // Try to add the donation
            int donationID = Controller.addDonation(addDonationRequest.getDonatorCNP(), addDonationRequest.getQuantity(),
                    addDonationRequest.getStatus(), addDonationRequest.getBloodID());

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
            InputValidator.validatePersonnelInput(updateDonationRequest);

            // Try to update the donation
            Controller.updateDonation(updateDonationRequest.getDonationID(), updateDonationRequest.getDonatorCNP(),
                    updateDonationRequest.getQuantity(), updateDonationRequest.getStatus(), updateDonationRequest.getBloodID());

            // Try to return the donation by the id it was updated
            return Controller.getDonationByID(updateDonationRequest.getDonationID());
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle update donation: " + ex.getMessage());
        }
    }

    public static Donation updateDonationStatus(String input) throws HandlingException {
        try {
            // Try to construct the update donation request
            UpdateDonationStatusRequest updateDonationRequest = new Gson().fromJson(input, UpdateDonationStatusRequest.class);

            // Validate input and get donor
            InputValidator.validatePersonnelInput(updateDonationRequest);

            // Try to update the donation
            Donation don = Controller.getDonationByID(updateDonationRequest.getDonationID());
            don.setStatus(Donation.DonationStatus.getByStatusID(updateDonationRequest.getStatusID()));
            Controller.updateDonation(don.getId(), don.getCnp(),
                    don.getQuantity(), updateDonationRequest.getStatusID(), don.getBloodID());

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
            InputValidator.validatePersonnelInput(messageRequest);

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

    public static List<DonationsResponse> getAllDonations(String input) throws HandlingException {
        MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

        // Validate input
        InputValidator.validatePersonnelInput(messageRequest);

        List<DonationsResponse> donationsResponses = new ArrayList<>();
        List<Donation> lst = Controller.getAllDonations();

        for (Donation don : lst) {
            Donator donator = Controller.getDonatorByCnp(don.getCnp());
            if (donator == null)
                continue;
            Blood blood = Controller.getBloodByID(don.getBloodID());
            if (blood == null)
                continue;
            Location location = Controller.getLocationById(don.getLocationid());
            if (location == null)
                location = new Location("dummy", 0, 0, 0);
            DonationsResponse donationsResponse = new DonationsResponse(don.getId(), donator.getCnp(), don.getQuantity(), don.getStatus(), blood.getBloodType(), donator.getName(), new SimpleDateFormat("dd-MM-yyyy").format(blood.getReceivedDate()), location.getName());
            donationsResponses.add(donationsResponse);
        }
        return donationsResponses;

    }

    public static List<DonationsResponse> getAllDonationsByCnp(String input) throws HandlingException {
        MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

        // Validate input
        InputValidator.validateDonatorInput(messageRequest);

        //String cnp = messageRequest.getMessage();
        List<DonationsResponse> donationsResponses = new ArrayList<>();
        List<Donation> lst = Controller.getAllDonations();

        for (Donation don : lst) {
            Donator donator = Controller.getDonatorByCnp(don.getCnp());
            if (donator == null)
                continue;
            Blood blood = Controller.getBloodByID(don.getBloodID());
            if (blood == null)
                continue;
            Location location = Controller.getLocationById(don.getLocationid());
            if (location == null)
                location = new Location("dummy", 0, 0, 0);
            DonationsResponse donationsResponse = new DonationsResponse(don.getId(), donator.getCnp(), don.getQuantity(), don.getStatus(), blood.getBloodType(), donator.getName(), new SimpleDateFormat("dd-MM-yyyy").format(blood.getReceivedDate()), location.getName());
            donationsResponses.add(donationsResponse);
        }

        return donationsResponses;

    }


    public static SuccessResponse receiveDonation(String input) throws HandlingException {
        try {
            // Try to construct the add donation request
            ReceiveDonationRequest receiveDonationRequest = new Gson().fromJson(input, ReceiveDonationRequest.class);

            // Validate input and get donor
            InputValidator.validatePersonnelInput(receiveDonationRequest);

            String cnp = receiveDonationRequest.getCnp();
            String name = receiveDonationRequest.getName();
            String bloodType = receiveDonationRequest.getBloodType();
            Donator d = Controller.getDonatorByCnp(cnp);
            Integer donatorId;

            if (d == null) {
                d = new Donator("@placeholder", "placehold", null, cnp, name, "", "");
                d.setBloodType(bloodType);
                donatorId = Controller.addDonator(cnp, "@placeholder", name, bloodType, "", null, "", "");
            } else {
                donatorId = d.getId();
            }

            Integer totalBloodQty = receiveDonationRequest.getBloodQuantity() + receiveDonationRequest.getPlasmaQuantity() +
                    receiveDonationRequest.getRedCellsQuantity() + receiveDonationRequest.getThrombocitesQuantity();


            Integer bloodId = Controller.addBlood(bloodType);
            Blood blood = Controller.getBloodByID(bloodId);
            Controller.addDonation(cnp, totalBloodQty, 0, bloodId);

            Integer plasmaQty = receiveDonationRequest.getPlasmaQuantity();
            Integer redCellsQty = receiveDonationRequest.getRedCellsQuantity();
            Integer thrombocitesQty = receiveDonationRequest.getThrombocitesQuantity();

            if (plasmaQty > 0) {
                Controller.addBloodPart(Plasma.class, bloodId, blood.getReceivedDate(), plasmaQty.floatValue());
            }
            if (redCellsQty > 0) {
                Controller.addBloodPart(RedCells.class, bloodId, blood.getReceivedDate(), redCellsQty.floatValue());
            }
            if (thrombocitesQty > 0) {
                Controller.addBloodPart(Thrombocites.class, bloodId, blood.getReceivedDate(), thrombocitesQty.floatValue());
            }


           // System.out.println(receiveDonationRequest.toString());

            return new SuccessResponse(true, "Donation succesfull");
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle add donation: " + ex.getMessage());
        }
    }


}
