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
    private final static String BLOODPART_PATH = "/bloodpart";
    private final static String PLASMA_PATH = "/plasma";
    private final static String REDCELLS_PATH = "/redcells";
    private final static String THROMBOCITES_PATH = "/thrombocites";

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
}