package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Patient;
import com.cyberschnitzel.Domain.Entities.Personnel;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddPatientRequest;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Domain.Transport.Requests.UpdatePatientRequest;
import com.google.gson.Gson;

import java.util.List;

public class PatientHandlers {
    public static Patient addPatient(String input) throws HandlingException {
        try {
            AddPatientRequest addPatientRequest = new Gson().fromJson(input, AddPatientRequest.class);
            int patientID = Controller.addPatient(addPatientRequest.getName(), addPatientRequest.getCnp());
            if (patientID == -1) throw new Exception("Patient wasn't added, ID != -1!");
            return Controller.getPatientById(patientID);
        } catch (Exception e) {
            throw new HandlingException("Failed handle add patient");
        }
    }

    public static Patient updatePatient(String input) throws HandlingException {
        try {
            UpdatePatientRequest updatePatientRequest = new Gson().fromJson(input, UpdatePatientRequest.class);
            Controller.updatePatient(updatePatientRequest.getPatientID(), updatePatientRequest.getCnp(),
                    updatePatientRequest.getPassword());
            return Controller.getPatientById(updatePatientRequest.getPatientID());
        } catch (Exception e) {
            throw new HandlingException("failed to handle updatePatient");
        }
    }

    public static Patient deletePatient(String input, int patientID) throws HandlingException {
        try {
            // Try to parse the input
            MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);

            // Validate input
            InputValidator.validatePersonnelInput(messageRequest);

            // Try to fetch the patient
            Patient patient = Controller.getPatientById(patientID);
            if (patient == null) throw new Exception("Inexistent patient");

            // Delete the patient
            Controller.deletePatient(patientID);
            return patient;
        } catch (Exception e) {
            throw new HandlingException("Failed to handle delete Patient");
        }
    }


}
