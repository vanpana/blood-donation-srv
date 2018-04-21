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
     * @param input - AddDonationRequest as a JSON
     * @return Response code: 200, body: the added donation if the task was successful
     */
    // Endpoint handlers
    @POST
    @Path(DONATIONS_PATH)
    public Response addDonation(String input) {
        return Handler.handle(() -> DonationHandlers.addDonation(input), DONATIONS_PATH, input);
    }

    @PUT
    @Path(DONATIONS_PATH)
    public Response updateDonation(String input) {
        return Handler.handle(() -> DonationHandlers.updateDonation(input), DONATIONS_PATH, input);
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
    public Response getDonation(@PathParam(PARAM) int id) {
        return Handler.handle(() -> Controller.getDonationByID(id), DONATIONS_PATH, String.valueOf(id));
    }
}