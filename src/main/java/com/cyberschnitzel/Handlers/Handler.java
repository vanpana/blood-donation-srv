package com.cyberschnitzel.Handlers;


import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

public class Handler {

    /**
     * Method that logs input, gets a string from the execution of the passed function, logs the response and returns
     * a Response with the corresponding status and message.
     * @param func - The function that should be called. Must return a string
     * @param endpoint - The url where the request came from
     * @param input - Input body (if necessary)
     * @return response - With code + body
     */
    public static Response handle(Callable<String> func, String endpoint, String input) {
        Response response;
        String output;
        // Log the input for debugging
        System.out.println(endpoint + ": " + input);

        // Run the designated function and catch any exception
        try {
            output = func.call();
            response = Response.status(200).entity(output).build();
        } catch (Exception exception) {
            // Create a response based on the exception. For example, 400 means bad request
            response = Response.status(400).build();
        }

        // Log response
        System.out.println("Response: " + response.getStatus() + " with body: " + response.getEntity());

        // Return response
        return response;
    }

    public static Response handle(Callable<String> func, String endpoint) {
        return handle(func, endpoint, "");
    }
}
