package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddBloodRequest;
import com.google.gson.Gson;

public class BloodHandlers {
    /**
     * Checks the donator and adds a blood sample
     *
     * @param input - Should be AddBloodRequest as a JSON
     * @return the blood object created
     */
    // TODO: In this case, donator should be changed to personnel
    public static Blood addBlood(String input) throws HandlingException {
        try {
            // Try to construct the add blood request
            AddBloodRequest addBloodRequest = new Gson().fromJson(input, AddBloodRequest.class);

            // Validate input
            InputValidator.validatePersonnelInput(addBloodRequest);

            // Try to add the blood sample
            int bloodID = Controller.addBlood(addBloodRequest.getBloodType());

            // Throw exception if blood couldn't be added
            if (bloodID == -1) throw new Exception("Blood wasn't added, ID = -1!");

            // Try to return the blood by the id it was created
            return Controller.getBloodByID(bloodID);
        } catch (Exception ex) {
            throw new HandlingException("Failed to handle add blood: " + ex.getMessage());
        }
    }
}
