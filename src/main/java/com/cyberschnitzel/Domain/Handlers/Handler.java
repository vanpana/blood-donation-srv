package com.cyberschnitzel.Domain.Handlers;


import com.cyberschnitzel.Domain.Exceptions.HandlingException;
import com.cyberschnitzel.Domain.Transport.Responses.SuccessResponse;
import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

public class Handler {
    /**
     * Method that logs input, gets a string from the execution of the passed function, logs the response and returns
     * a MessageResponse with the corresponding status and message.
     *
     * @param func     - The function that should be called. Must return a string
     * @param endpoint - The url where the request came from
     * @param input    - Input body (if necessary)
     * @return response - With code + body. If code is 200, the body will be a {@link SuccessResponse SuccessResponse.class}.
     */
    public static Response handle(Callable func, String endpoint, String input) {
        Response response;
        int responseCode;
        String responseBody;

        // The object type doesn't matter, Gson will take care of the serialization
        Object output;

        // Log the input for debugging
        System.out.println(endpoint + ": " + input);

        // Run the designated function and catch any exception
        try {
            output = func.call();

            if (output == null) throw new NullPointerException("Not found!");

            // Build the transport entity
            SuccessResponse successResponse = new SuccessResponse(true, new Gson().toJson(output));
            responseCode = 200;
            responseBody = new Gson().toJson(successResponse);
        } catch (HandlingException he) {
            SuccessResponse successResponse = new SuccessResponse(false, he.getMessage());
            responseCode = 200;
            responseBody = new Gson().toJson(successResponse);
        } catch (NullPointerException npe) {
            // If no data was found, return status 404
            responseCode = 404;
            responseBody = "";
        } catch (Exception exception) {
            // Create a response based on the exception. For example, 400 means bad request
            responseCode = 500;
            responseBody = "";
        }

        // Build the response entity
        response = Response.status(responseCode).entity(responseBody).header("Content-Type", "text/json; charset=UTF-8").build();

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
