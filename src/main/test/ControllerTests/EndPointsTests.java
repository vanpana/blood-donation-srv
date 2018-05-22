package ControllerTests;

import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Domain.Transport.Responses.SuccessResponse;
import com.cyberschnitzel.Endpoints.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EndPointsTests {
    @Test
    public void testGetBlood() {
        Response response = Endpoints.getBlood();
        if (response.getStatus() == 200) {
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Operation did not succesed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<Blood>>() {}.getType());
                assertTrue(true, "Fetched blood list");
            }catch (Exception e){
                fail("Invalid response body.");
            }


        } else {
            fail("Status should be 200.");
        }
    }
}
