package com.cyberschnitzel.Endpoints;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Handlers.Handler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * The path is set in web.xml. The base path will be *ip*:8080/api/*
 */
@Path("")
public class Endpoints {
    // Endpoints path
    private final static String DONATIONS_PATH = "/donations";

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    // Endpoint handlers

    /**
     * Method to get all donations
     *
     * @return Response
     */
    @GET
    @Path(DONATIONS_PATH)
    public Response getDonations() {
        return Handler.handle(Controller::getAllDonations, DONATIONS_PATH);
    }

    /**
     * Method to get donation by ID
     *
     * @return Response
     */
    @GET
    @Path(DONATIONS_PATH + PATH_PARAM)
    public Response getDonation(@PathParam(PARAM) int id) {
        return Handler.handle(() -> Controller.getDonationByID(id), DONATIONS_PATH, String.valueOf(id));
    }
}