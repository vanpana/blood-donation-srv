package com.cyberschnitzel.Endpoints;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Handlers.DonationHandlers;
import com.cyberschnitzel.Domain.Handlers.Handler;

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

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    /**
     * POST method to add a donation
     *
     * @param addDonationRequestJson - AddDonationRequest as a JSON
     * @return Response code: 200, body: the added donation if the task was successful
     */
    // Endpoint handlers
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

    // Note: Important entities should not be fetched through GET method with no credentials checking, but for now, we'll
    // keep it like this.
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
    public Response getDonation(@PathParam(PARAM) int donationID) {
        return Handler.handle(() -> Controller.getDonationByID(donationID), DONATIONS_PATH, String.valueOf(donationID));
    }
}