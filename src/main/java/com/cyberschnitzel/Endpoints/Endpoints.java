package com.cyberschnitzel.Endpoints;


import com.cyberschnitzel.Domain.Handlers.Demo.Demo;
import com.cyberschnitzel.Domain.Handlers.Handler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import static com.cyberschnitzel.Domain.Handlers.Demo.Demo.getUser;
import static com.cyberschnitzel.Domain.Handlers.Demo.Demo.hello;

/**
 * The path is set in web.xml. The base path will be *ip*:8080/api/*
 */
@Path("")
public class Endpoints {
    // Endpoints path
    private final static String HELLO = "/hello";
    private final static String USERS = "/users";

    // Path parameters regex
    private final static String PATH_PARAM = "/{param}";
    private final static String PARAM = "param";

    // Endpoint handlers
    /**
     * Method that is the endpoint to GET /hello request and calls function hello with no params.
     * @return Response
     */
    @GET
    @Path(HELLO)
    public Response sayHello() {
        return Handler.handle(Demo::hello, HELLO);
    }

    /**
     * Method that is the endpoint to POST /hello request and calls function hello with a String param.
     * @param input The String to be added greeting message
     * @return Response
     */
    @POST
    @Path(HELLO)
    public Response sayHello(String input) {
        return Handler.handle(() -> hello(input), HELLO, input);
    }

    /**
     * Method that returns all the users.
     * @return Response
     */
    @GET
    @Path(USERS)
    public Response getUsers() {
        return Handler.handle(Demo::getUsers, USERS);
    }

    /**
     * Method that returns a user by name with GET.
     * Make the GET request to localhost:8080/rest/users/user1 to get user with name "user1"
     * @return Response
     */
    @GET
    @Path(USERS + PATH_PARAM)
    public Response getUsers(@PathParam(PARAM) String name) {
        return Handler.handle(() -> getUser(name), USERS, name);
    }
}