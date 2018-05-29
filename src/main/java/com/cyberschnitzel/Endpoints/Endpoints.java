package com.cyberschnitzel.Endpoints;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.BloodPart;
import com.cyberschnitzel.Domain.Handlers.BloodPartHandlers;
import com.cyberschnitzel.Domain.Handlers.DonationHandlers;
import com.cyberschnitzel.Domain.Handlers.Handler;
import org.atmosphere.config.service.Post;
import org.atmosphere.config.service.Put;
import com.cyberschnitzel.Domain.Handlers.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    private final static String DETAILED_DONATIONS_PATH = "/detailed-donations";
    private final static String BLOODPART_PATH = "/bloodpart";
    private final static String PLASMA_PATH = "/plasma";
    private final static String REDCELLS_PATH = "/redcells";
    private final static String THROMBOCITES_PATH = "/thrombocites";
    private final static String PATIENTS_PATH = "/patients";
    private final static String LOGIN_PATH = "/login";
    private final static String PERSONNEL_PATH = "/personnel";
    private final static String STATUS_PATH = "/status";
	private final static String RECEIVE = "/receive";
	private final static String REQUESTS_PATH = "/requests";
	private final static String REQUEST_AVAILABLE_BLOOD_PATH = "/requests/available";
	private final static String USE_BLOOD_PATH = "/used";

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    // Note: Important entities should not be fetched through GET method with no credentials checking, but for now, we'll
    // keep it like this.

    //<editor-fold desc="Blood endpoints">
    /**
     * POST method to add a blood sample
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


    //params cnp, 4 x quant, status auto, bloodType, name, dob, recv auto
	//TODO notif succeful
	@POST
	@Path(DONATIONS_PATH + RECEIVE)
	public Response receiveDonation(String receiveDonationRequestJson) {
		return Handler.handle(() -> DonationHandlers.receiveDonation(receiveDonationRequestJson), DONATIONS_PATH,
				receiveDonationRequestJson);
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


    @PUT
	@Path(DONATIONS_PATH + STATUS_PATH)
	public Response updateDonationStatus(String updateDonationStatusRequestJson)
	{
		return Handler.handle(() -> DonationHandlers.updateDonationStatus(updateDonationStatusRequestJson), DONATIONS_PATH + STATUS_PATH, updateDonationStatusRequestJson);
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
    @POST
    @Path(DETAILED_DONATIONS_PATH)
    public Response getDonations(String messageRequestJson) {
        return Handler.handle(() -> DonationHandlers.getAllDonations(messageRequestJson), DONATIONS_PATH);
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

	//<editor-fold desc="Bloodpart endpoints">
	@GET
	@Path(BLOODPART_PATH + PLASMA_PATH)
	public Response getAllPlasma(){
		return Handler.handle(() -> Controller.getBloodPart("Plasma"), BLOODPART_PATH);
	}

	@GET
	@Path(BLOODPART_PATH + REDCELLS_PATH)
	public Response getAllRedCells(){
		return Handler.handle(() -> Controller.getBloodPart("RedCells"), BLOODPART_PATH);
	}

	@GET
	@Path(BLOODPART_PATH + THROMBOCITES_PATH)
	public Response getAllThrombocites(){
		return Handler.handle(() -> Controller.getBloodPart("Thrombocites"), BLOODPART_PATH);
	}

	@POST
	@Path(BLOODPART_PATH)
	public Response getAllBloodParts(String messageRequestJson){
    	return Handler.handle(() -> BloodPartHandlers.getAllBloodParts(messageRequestJson), BLOODPART_PATH);
	}

	@POST
	@Path(REQUESTS_PATH)
	public Response getAllRequests(String messageRequestJson){
		return Handler.handle(() -> RequestsHandlers.getAllRequests(messageRequestJson), REQUESTS_PATH);
	}



	@DELETE
	@Path(BLOODPART_PATH + PLASMA_PATH + PATH_PARAM)
	public Response deletePlasma(@PathParam(PARAM) int id)
	{
		return Handler.handle(() -> Controller.deleteBloodPart("Plasma", id), BLOODPART_PATH);
	}

	@DELETE
	@Path(BLOODPART_PATH + REDCELLS_PATH + PATH_PARAM)
	public Response deleteRedCells(@PathParam(PARAM) int id)
	{
		return Handler.handle(() -> Controller.deleteBloodPart("RedCells", id), BLOODPART_PATH);
	}

	@DELETE
	@Path(BLOODPART_PATH + THROMBOCITES_PATH + PATH_PARAM)
	public Response deleteThrombocites(@PathParam(PARAM) int id)
	{
		return Handler.handle(() -> Controller.deleteBloodPart("Thrombocites", id), BLOODPART_PATH);
	}

//	@POST
//	@Path(BLOODPART_PATH)
//	public Response addBloodPart(String addBloodPartRequestJson) {
//		return Handler.handle(() -> BloodPartHandlers.addBloodPart(addBloodPartRequestJson), BLOODPART_PATH,
//				addBloodPartRequestJson);
//	}

	@PUT
	@Path(BLOODPART_PATH + PATH_PARAM)
	public Response updateBloodPart(@PathParam(PARAM) int id,String updateBloodPartRequestJson) {
		return Handler.handle(() -> BloodPartHandlers.updateBloodPart(updateBloodPartRequestJson,id), BLOODPART_PATH,
				updateBloodPartRequestJson);
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

	@POST
	@Path(PERSONNEL_PATH + LOGIN_PATH)
	public Response loginPatient(String messageRequestJson){
		return Handler.handle(() -> PersonnelHandlers.checkPersonnelLogin(messageRequestJson), PERSONNEL_PATH + LOGIN_PATH,
				messageRequestJson);
	}

	@POST
	@Path(DONATORS_PATH + LOGIN_PATH)
	public Response loginUser(String messageRequestJson){
		return Handler.handle(() -> UserHandlers.checkDonatorLogin(messageRequestJson), DONATORS_PATH + LOGIN_PATH,
				messageRequestJson);
	}

	@POST
	@Path(DONATORS_PATH)
	public Response getDonatorByCnp(String messageRequestJson)
	{
		return Handler.handle(() -> UserHandlers.getDonatorByCnp(messageRequestJson), DONATORS_PATH,
				messageRequestJson);
	}

	@POST
	@Path(REQUEST_AVAILABLE_BLOOD_PATH)
	public Response getAllAvailabeBloodForRequest(String messageRequestJson)
	{
		return Handler.handle(() -> RequestsHandlers.getAllAvailableBloodForRequest(messageRequestJson), REQUEST_AVAILABLE_BLOOD_PATH,
				messageRequestJson);
	}


	@POST
	@Path(USE_BLOOD_PATH)
	public Response useBlood(String useBloodRequestJson)
	{
		return Handler.handle(() -> RequestsHandlers.useBlood(useBloodRequestJson), REQUEST_AVAILABLE_BLOOD_PATH,
				useBloodRequestJson);
	}


}
