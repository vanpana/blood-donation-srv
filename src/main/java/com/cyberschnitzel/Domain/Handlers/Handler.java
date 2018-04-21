package com.cyberschnitzel.Domain.Handlers;


import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

public class Handler {
    /**
     * Method that logs input, gets a string from the execution of the passed function, logs the response and returns
     * a MessageResponse with the corresponding status and message.
     * @param func - The function that should be called. Must return a string
     * @param endpoint - The url where the request came from
     * @param input - Input body (if necessary)
     * @return response - With code + body
     */
    public static Response handle(Callable func, String endpoint, String input) {
        Response response;

        // The object type doesn't matter, Gson will take care of the serialization
        Object output;

        // Log the input for debugging
        System.out.println(endpoint + ": " + input);

        // Run the designated function and catch any exception
        try {
            output = func.call();

            if (output == null) throw new NullPointerException("Not found!");

            // This will serialize any object to JSON.
            String body = new Gson().toJson(output);

            // Build the successful response
            response = Response.status(200).entity(body).build();
        } catch (NullPointerException npe) {
            // If no data was found, return status 404
            response = Response.status(404).entity(npe.getMessage()).build();
        } catch (Exception exception) {
            // Create a response based on the exception. For example, 400 means bad request
            response = Response.status(400).build();
        }

        // Log response
        System.out.println("MessageResponse: " + response.getStatus() + " with body: " + response.getEntity());

        // Return response
        return response;
    }

    /**
     * Handle function with no input params
     */
    public static Response handle(Callable func, String endpoint) {
        return handle(func, endpoint, "");
    }
}
