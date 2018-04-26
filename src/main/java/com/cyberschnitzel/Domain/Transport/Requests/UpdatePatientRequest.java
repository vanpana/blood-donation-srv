package com.cyberschnitzel.Domain.Transport.Requests;

public class UpdatePatientRequest extends AddPatientRequest {
    int patientID;

    public UpdatePatientRequest(String email ,String password, String token, String cnp, String name){
        super(email, password, token, cnp, name);
    }


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
