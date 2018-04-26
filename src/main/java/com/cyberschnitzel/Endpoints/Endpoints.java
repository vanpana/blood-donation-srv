package com.cyberschnitzel.Endpoints;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Handlers.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * The path is set in web.xml. The base path will be *ip*:8080/api/*
 */
@Path("")
public class Endpoints {
    // Endpoints path
    private final static String DONATORS_PATH = "/donators";
    private final static String BLOOD_PATH = "/blood";
    private final static String DONATIONS_PATH = "/donations";
    private final static String PATIENTS_PATH = "/patients";

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    // Note: Important entities should not be fetched through GET method with no credentials checking, but for now, we'll
    // keep it like this.

    //<editor-fold desc="Blood endpoints">
    /**
     * POST method to add a blood samlpe
     *
     * @param addBloodRequestJson - AddBloodRequest as a JSON
     * @return Response code: 200, body: the added blood if the task was successful
     */
    @POST
    @Path(BLOOD_PATH)
    public Response addBlood(String addBloodRequestJson) {
        return Handler.handle(() -> BloodHandlers.addBlood(addBloodRequestJson), BLOOD_PATH,
                addBloodRequestJson);
    }

    /**
     * Method to get all blood samples
     *
     * @return MessageResponse
     */
    @GET
    @Path(BLOOD_PATH)
    public Response getBlood() {
        return Handler.handle(Controller::getAllBlood, BLOOD_PATH);
    }

    /**
     * Method to get blood sample by ID
     *
     * @return MessageResponse
     */
    @GET
    @Path(BLOOD_PATH + PATH_PARAM)
    public Response getBloodByID(@PathParam(PARAM) int bloodID) {
        return Handler.handle(() -> Controller.getBloodByID(bloodID), BLOOD_PATH, String.valueOf(bloodID));
    }
    //</editor-fold>

    //<editor-fold desc="Donation endpoints">
    /**
     * POST method to add a donation
     *
     * @param addDonationRequestJson - AddDonationRequest as a JSON
     * @return Response code: 200, body: the added donation if the task was successful
     */
    @POST
    @Path(DONATIONS_PATH)
    public Response addDonation(String addDonationRequestJson) {
        return Handler.handle(() -> DonationHandlers.addDonation(addDonationRequestJson), DONATIONS_PATH,
                addDonationRequestJson);
    }

    /**
     * PUT method to update a donation
     *
     * @param updateDonationRequestJson - UpdateDonationRequest as a JSON
     * @return Response code: 200, body: the updated donation if the task was successful
     */
    @PUT
    @Path(DONATIONS_PATH)
    public Response updateDonation(String updateDonationRequestJson) {
        return Handler.handle(() -> DonationHandlers.updateDonation(updateDonationRequestJson), DONATIONS_PATH,
                updateDonationRequestJson);
    }

    /**
     * DELETE method to delete a donation
     *
     * @param messageRequestJson - MessageRequest as a JSON with empty message
     * @return Response code: 200, body: the deleted donation if the task was successful
     */
    @DELETE
    @Path(DONATIONS_PATH + PATH_PARAM)
    public Response deleteDonation(@PathParam(PARAM) int id, String messageRequestJson) {
        return Handler.handle(() -> DonationHandlers.deleteDonation(messageRequestJson, id),
                DONATIONS_PATH + PATH_PARAM, String.valueOf(id) + " " + messageRequestJson);
    }

    /**
     * Method to get all donations
     *
     * @return MessageResponse
     */
    @GET
    @Path(DONATIONS_PATH)
    public Response getDonations() {
        return Handler.handle(Controller::getAllDonations, DONATIONS_PATH);
    }

    /**
     * Method to get donation by ID
     *
     * @return MessageResponse
     */
    @GET
    @Path(DONATIONS_PATH + PATH_PARAM)
    public Response getDonationByID(@PathParam(PARAM) int donationID) {
        return Handler.handle(() -> Controller.getDonationByID(donationID), DONATIONS_PATH, String.valueOf(donationID));
    }
  //</editor-fold>

    //<editor-fold desc="Patient endpoints">
    @POST
    @Path(PATIENTS_PATH)
    public Response addPatient(String addPatientRequestJson){
        return Handler.handle(() -> PatientHandlers.addPatient(addPatientRequestJson), PATIENTS_PATH,
                addPatientRequestJson);
    }

    @PUT
    @Path(PATIENTS_PATH)
    public Response updatePatient(String updatePatientRequestJson){
        return Handler.handle(() -> PatientHandlers.updatePatient(updatePatientRequestJson), PATIENTS_PATH,
            updatePatientRequestJson);
    }

    @DELETE
    @Path(PATIENTS_PATH + PATH_PARAM)
    public Response deletePatient(@PathParam(PARAM) int id, String messageRequestJson){
        return Handler.handle(() -> PatientHandlers.deletePatient(messageRequestJson, id),
                PATIENTS_PATH + PATH_PARAM, String.valueOf(id) + messageRequestJson);
    }

    @GET
    @Path(PATIENTS_PATH + PATH_PARAM)
    public Response getPatientByID(@PathParam(PARAM) int id){
        return Handler.handle(() -> Controller.getPatientById(id), PATIENTS_PATH, String.valueOf(id));
    }

    @GET
    @Path(PATIENTS_PATH)
    public Response getPatients(){
        return Handler.handle(Controller::getAllPatients, PATIENTS_PATH);
    }
    //</editor-fold>
}
