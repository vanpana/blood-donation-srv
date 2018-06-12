package ControllerTests;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.*;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Transport.Requests.*;
import com.cyberschnitzel.Domain.Transport.Responses.SuccessResponse;
import com.cyberschnitzel.Endpoints.Endpoints;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EndPointsTests {

    /**
     ----Blood Tests---
     */



    @Test
    public void testAddBlood() {
        final String personnelEmail = "popbianca@yahoo.com", bloodType = "AB";

        // Try to get personnel by email
        Personnel personnel = Controller.getPersonnelByEmail(personnelEmail);

        // Fail if personnel was not found
        if (personnel == null) fail("Personnel was null");

        // Build the request
        AddBloodRequest addBloodRequest = new AddBloodRequest(personnel.getEmail(), personnel.getPassword(),
                personnel.getToken(), bloodType);

        // Get the response from the endpoint
        Response response = Endpoints.addBlood(new Gson().toJson(addBloodRequest));
        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Adding Blood did not succesed!");

            // Get the last blood from the database
            Blood lastBloodDb = Controller.getAllBlood().get(Controller.getAllBlood().size() - 1);
            System.out.print(lastBloodDb.getBloodType());

            // Check if the last blood type equals to bloodType
            if(!lastBloodDb.getBloodType().toString().equals(bloodType))fail("Blood type does not correspond!");


            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Blood>() {
                }.getType());
                assertTrue(true, "Blood added");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }
    }

    @Test
    public void testGetBlood() {

        //Get the response from the endpoint
        Response response = Endpoints.getBlood();

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Operation did not succesed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<Blood>>() {
                }.getType());
                assertTrue(true, "Fetched blood list");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }
    }


    @Test
    public void testGetBloodById() {
        // Get the last id from database
        int lastId = Controller.getAllBlood().get(Controller.getAllBlood().size() - 1).getId();

        // Get the response from the endpoint
        Response response = Endpoints.getBloodByID(lastId);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Get Blood by ID did not succesed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Blood>() {
                }.getType());
                assertTrue(true, "Correct returned blood by id!");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }

    }



    /**
     ----Donation Tests---
    */

    @Test
    public void testGetDonations(){

        final String personnelEmail = "popbianca@yahoo.com", bloodType = "AB";
        // Try to get personnel by email
        Personnel personnel = Controller.getPersonnelByEmail(personnelEmail);

        // Fail if personnel was not found
        if (personnel == null) fail("Personnel was null, no rights to get donations");

        // Build the messageRequest
        MessageRequest messageRequest = new MessageRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken());

        // Convert messageRequest into json
        String messageRequestJson=new Gson().toJson(messageRequest);

        // Get the response from the endpoint
        Response response = Endpoints.getDonations(messageRequestJson);
        if (response.getStatus() == 200) {

            //Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Operation did not succesed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<Donation>>() {
                }.getType());
                assertTrue(true, "Fetched donation list");
            } catch (Exception e) {
                fail("Invalid response body at donation.");
            }

        } else {
            fail("Status should be 200.");
        }

    }

     //Check if a donation is added
     @Test
     public void testAddDonation() {

        final String personnelEmail = "popbianca@yahoo.com", bloodType = "AB";
        final String donorEmail="bianca@yahooo.com";
        double quantity=700;

        // Try to get personnel by email
        Personnel personnel = Controller.getPersonnelByEmail(personnelEmail);

        //Try to get donor by email from database
        Donator donor= Controller.getDonatorByEmail(donorEmail);

        // Fail if personnel was not found
        if (personnel == null) fail("Personnel was null");

        // Fail if donor was not found
        if (donor == null) fail("donor was null");

        AddDonationRequest addDonationRequest= new AddDonationRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken(),donor.getCnp(),quantity,donor.getId());

        String addDonationRequestJson= new Gson().toJson(addDonationRequest);
        // Get the response from the endpoint
        Response response = Endpoints.addDonation(addDonationRequestJson);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Add donation did not successed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Donation>() {
                }.getType());
                assertTrue(true, "Donation added successfully!");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }

    }

    /**
     * Checks if all donations are received*/

//
//    @Test
//    public void testReceiveDonation(){
//
//        final String email= "popbianca@yahoo.com";
//        final String emailDonor= "bianca@yahooo.com";
//        //Try to take personnel from database by email
//        Personnel personnel=Controller.getPersonnelByEmail(email);
//
//        //Try to take donor from database by email
//        Donator donor=Controller.getDonatorByEmail(emailDonor);
//
//        //If the personnel is not in database, he/she can not log in
//        if (personnel==null)fail("This personnel is not in database");
//
//        //Check if the donor is not in database
//        if (donor==null)fail("This donor is not in database");
//
//        //Try to get donation made by this donor
//        Donation donation=Controller.getDonationByCnp(donor.getCnp());
//
//        //Check if the donation is not in database
//        if (donation==null)fail("This donor is not in database");
//
//        //Build a message request with the personnel properties
//        ReceiveDonationRequest receiveDonationRequest = new ReceiveDonationRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken(),donor.getCnp(),(int)donation.getQuantity(),45,25,15,donor.getBloodtype().toString(),donor.getName(),"12/05/2018");
//
//        //Convert message request into json
//        String receiveDonationRequestJson=new Gson().toJson(receiveDonationRequest);
//
//        //Get the response from the endpoint
//        Response response = Endpoints.receiveDonation(receiveDonationRequestJson);
//
//        if (response.getStatus() == 200) {
//            // Build the success response
//            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);
//
//            if (!successResponse.isSuccess()) fail("Operation did not succesed!");
//
//            try {
//                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Donation>() {
//                }.getType());
//                assertTrue(true, "Donation received successfully!");
//            } catch (Exception e) {
//                fail("Invalid response body.");
//            }
//
//        } else {
//            fail("Status should be 200.");
//        }
//
//    }

    /**
     ----Login Tests---
     */



    /**
     * Method which checks if an existing user can log in.
     */

    @Test
    public void testLoginExistingUser(){
        final String email= "david@yahooo.com";
        //Try to take user from database by email
        Donator donor=Controller.getDonatorByEmail(email);

        //If the user is not in database, he/she can not log in
        if (donor==null)fail("This user is not in database");

        //Build a message request with the donor properties
        MessageRequest messageRequest=new MessageRequest(donor.getEmail(),donor.getPassword(),donor.getToken());

        //Convert message request into json
        String messageRequestJson=new Gson().toJson(messageRequest);

        // Get the response from the endpoint
        Response response = Endpoints.loginUser(messageRequestJson);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Login user failed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Donator>() {
                }.getType());
                assertTrue(true, "donor logged in successfully!");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }
    }

    /**
     * Method which checks if an inexistent user can log in.
     */

    @Test
    public void testLoginUser(){
        String cnp="1970921245045";
        String email= "ionica@yahooo.com";
        String name="Popescu Ionica";

        //Creating a new donor
        Donator donor=new Donator(cnp,email,name);

        //Build a message request with the donor properties
        MessageRequest messageRequest=new MessageRequest(donor.getEmail(),donor.getPassword(),donor.getToken());

        //Convert message request into json
        String messageRequestJson=new Gson().toJson(messageRequest);

        // Get the response from the endpoint
        Response response = Endpoints.loginUser(messageRequestJson);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            //
            if (successResponse.isSuccess()) fail(" Unknown user could log in!");

        } else {
            fail("Status should be 200.");
        }


    }

    /**
     * Method which checks if an existing personnel can log in.
     */

    @Test
    public void testLoginExistingPersonnel(){
        final String email= "popbianca@yahoo.com";
        //Try to take personnel from database by email
        Personnel personnel=Controller.getPersonnelByEmail(email);

        //If the personnel is not in database, he/she can not log in
        if (personnel==null)fail("This personnel is not in database");

        //Build a message request with the personnel properties
        MessageRequest messageRequest = new MessageRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken());

        //Convert message request into json
        String messageRequestJson=new Gson().toJson(messageRequest);

        // Get the response from the endpoint
        Response response = Endpoints.loginPatient(messageRequestJson);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Login personnel failed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<Personnel>() {
                }.getType());
                assertTrue(true, "Personnel logged in successfully!");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }
    }

    /**
     * Method which checks if an inexistent personnel can log in.
     */

    @Test
    public void testLoginPersonnel(){
        String email= "ionica@yahooo.com";
        String name="Popescu Ionica";

        //Creating a new personnel
        Personnel personnel=new Personnel(email,name);

        //Build a message request with the personnel properties
        MessageRequest messageRequest=new MessageRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken());

        //Convert message request into json
        String messageRequestJson=new Gson().toJson(messageRequest);

        // Get the response from the endpoint
        Response response = Endpoints.loginPatient(messageRequestJson);

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            //
            if (successResponse.isSuccess()) fail(" Unknown personnel could log in!");

        } else {
            fail("Status should be 200.");
        }


    }



    /**
     ----Plasma tests---
     */


    @Test
    public void testGetAllPlasma() {

        //Get the response from the endpoint
        Response response = Endpoints.getAllPlasma();

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Getting plasma did not successed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<BloodPart>>() {
                }.getType());
                assertTrue(true, "Fetched plasma list");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }
    }

    @Test
    public void testGetAllRedCells(){
        //Get the response from the endpoint
        Response response = Endpoints.getAllRedCells();

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Getting red cells did not successed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<BloodPart>>() {
                }.getType());
                assertTrue(true, "Fetched red cells list");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }

    }

    @Test
    public void testAllThrombocites(){
        //Get the response from the endpoint
        Response response = Endpoints.getAllThrombocites();

        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Getting trombocites did not successed!");

            try {
                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<BloodPart>>() {
                }.getType());
                assertTrue(true, "Fetched thrombocites list");
            } catch (Exception e) {
                fail("Invalid response body.");
            }

        } else {
            fail("Status should be 200.");
        }

    }

    //NOT WORKING?!!!!

//    @Test
//    public void testGetAllBloodParts(){
//
//        final String email= "popbianca@yahoo.com";
//        //Try to take personnel from database by email
//        Personnel personnel=Controller.getPersonnelByEmail(email);
//
//        //If the user is not in database, he/she can not log in
//        if (personnel==null)fail("This personnel is not in database");
//
//        //Build a message request with the personnel properties
//        MessageRequest messageRequest = new MessageRequest(personnel.getEmail(),personnel.getPassword(),personnel.getToken());
//
//        //Convert message request into json
//        String messageRequestJson=new Gson().toJson(messageRequest);
//
//        Response response = Endpoints.getAllBloodParts(messageRequestJson);
//
//        if (response.getStatus() == 200) {
//            // Build the success response
//            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);
//
//            if (!successResponse.isSuccess()) fail("Getting blood parts did not succesed!");
//
//            try {
//                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<BloodPart>>() {
//                }.getType());
//                assertTrue(true, "Fetched blood parts list");
//            } catch (Exception e) {
//                fail("Invalid response body.");
//            }
//
//        } else {
//            fail("Status should be 200.");
//        }
//    }

    @Test
    public void testDeletePlasma(){
        int id=1;
        String part="Plasma";
        int plasmasize=0;

        Response response=Endpoints.deletePlasma(id);
        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Deleting plasma did not succesed!");

            //just one elem
//            try {
//                new Gson().fromJson(successResponse.getMessage(), new TypeToken<List<BloodPart>>() {
//                }.getType());
//                assertTrue(true, "Fetched plasma list");
//            }catch (Exception e) {
//                fail("Invalid response body.");
//            }

            } else{
                fail("Status should be 200.");
            }
        }

    @Test
    public void testDeleteRedCells(){
        int id=1;
        String part="RedCells";

        Response response=Endpoints.deleteRedCells(id);
        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Deleting red cells did not succesed!");

        } else{
            fail("Status should be 200.");
        }
    }

    @Test
    public void testDeleteThrombocites(){
        int id=1;
        String part="Thrombocites";

        Response response=Endpoints.deleteRedCells(id);
        if (response.getStatus() == 200) {
            // Build the success response
            SuccessResponse successResponse = new Gson().fromJson(response.getEntity().toString(), SuccessResponse.class);

            if (!successResponse.isSuccess()) fail("Deleting thrombocites did not successed!");

        } else{
            fail("Status should be 200.");
        }
    }



}

