package com.cyberschnitzel.Endpoints;


import com.cyberschnitzel.Handlers.Demo.Demo;
import com.cyberschnitzel.Handlers.Handler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import static com.cyberschnitzel.Handlers.Demo.Demo.getUser;
import static com.cyberschnitzel.Handlers.Demo.Demo.hello;

class EndpointsPath{
    public final static String HELLO = "/hello";
    public final static String USERS = "/users";


    public final static String PATH_PARAM = "/{param}";
    public final static String PARAM = "param";

    public static String getPathParam(String path) {
        return "/{" + path + "}";
    }
}

@Path("")
public class Endpoints {

    /**
     * Method that is the endpoint to GET /hello request and calls function hello with no params.
     * @return Response
     */
    @GET
    @Path(EndpointsPath.HELLO)
    public Response sayHello() {
        return Handler.handle(Demo::hello, EndpointsPath.HELLO);
    }

    /**
     * Method that is the endpoint to POST /hello request and calls function hello with a String param.
     * @param input The String to be added greeting message
     * @return Response
     */
    @POST
    @Path(EndpointsPath.HELLO)
    public Response sayHello(String input) {
        return Handler.handle(() -> hello(input), EndpointsPath.HELLO, input);
    }

    @GET
    @Path(EndpointsPath.USERS)
    public Response getUsers() {
        return Handler.handle(Demo::getUsers, EndpointsPath.USERS);
    }

    @GET
    @Path(EndpointsPath.USERS + EndpointsPath.PATH_PARAM)
    public Response getUsers(@PathParam(EndpointsPath.PARAM) String name) {
        return Handler.handle(() -> getUser(name), EndpointsPath.USERS, name);
    }
}