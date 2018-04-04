package com.cyberschnitzel.Endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * The path is set in web.xml. The base path will be *ip*:8080/api/*
 */
@Path("")
public class Endpoints {
    // Endpoints path

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    // Endpoint handlers
    /**
     * Dummy method
     * @return Response
     */
    @GET
    @Path("")
    public Response dummy() {
        return null;
    }
}