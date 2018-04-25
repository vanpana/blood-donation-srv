package com.cyberschnitzel.Domain.Handlers;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Patient;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Requests.AddPatientRequest;
import com.cyberschnitzel.Domain.Transport.Requests.MessageRequest;
import com.cyberschnitzel.Domain.Transport.Requests.UpdatePatientRequest;
import com.google.gson.Gson;

public class PatientHandlers {
    public static Patient addPatient(String input) throws HandlingException {
        try {
            AddPatientRequest addPatientRequest = new Gson().fromJson(input, AddPatientRequest.class);
            int idCuNumeSugestiv = Controller.addPersonnel(addPatientRequest.getName(), addPatientRequest.getCnp());
            if (idCuNumeSugestiv == -1) throw new Exception("Patient wasn't added, bla bla bla ID != -1! asigaosjf");
            return Controller.getPatientById(idCuNumeSugestiv);
        } catch (Exception e) {
            throw new HandlingException("Failed handle add patient");
        }
    }

    public static Patient updatePatient(String input) throws HandlingException{
        try{
            UpdatePatientRequest updatePatientRequest = new Gson().fromJson(input, UpdatePatientRequest.class);
            Controller.updatePatient(updatePatientRequest.getPatientID(), updatePatientRequest.getCnp(), updatePatientRequest.getPassword());
            return Controller.getPatientById(updatePatientRequest.getPatientID()) ;
        } catch (Exception e){
            throw new HandlingException("failed to handle updatePatient");
        }
    }

    public static Patient deletePatient(String input, int patientID) throws HandlingException{
        try {
            MessageRequest messageRequest = new Gson().fromJson(input, MessageRequest.class);
            Patient patient = Controller.getPatientById(patientID);
            if (patient == null) throw new Exception("Inexistent patient");
            Controller.deletePatient(patientID);
            return patient;
        }catch (Exception e){
            throw new HandlingException("failed to hangle delete Patient");
        }
    }
}
