package com.cyberschnitzel.Endpoints;


import com.cyberschnitzel.Handlers.Demo.Demo;
import com.cyberschnitzel.Handlers.Handler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static com.cyberschnitzel.Handlers.Demo.Demo.hello;

@Path("/hello")
public class Endpoints {
    private final static String HELLO = "/hello";

    /**
     * Method that is the endpoint to GET /hello request and calls function hello with no params.
     * @return Response
     */
    @GET
    public Response sayHello() {
        return Handler.handle(Demo::hello, HELLO);
    }

    /**
     * Method that is the endpoint to POST /hello request and calls function hello with a String param.
     * @param input The String to be added greeting message
     * @return Response
     */
    @POST
    public Response sayHello(String input) {
        return Handler.handle(() -> hello(input), HELLO, input);
    }
}