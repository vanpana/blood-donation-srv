package ControllerTests;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DonationTests {
    private int donatorID;

    @Before
    public void setup() throws ControllerException {
        donatorID = Controller.addDonator("1234567890", "example@email.com", "Test name");
    }

    @After
    public void teardown() {
        Controller.deleteDonator(donatorID);
    }

    @Test
    public void addCorrectDonationByCnp() {
        try {
            int bloodID = Controller.addBlood("ZERO");
            if (bloodID < 0) fail("Blood sample should've been added");

            int donationID = Controller.addDonation("1234567890", 12, 0, bloodID);
            assertTrue( "Donation successfully added!", donationID != -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }

    @Test
    public void addCorrectDonationByDonatorId() {
        try {
            int bloodID = Controller.addBlood("ZERO");
            if (bloodID < 0) fail("Blood sample should've been added");

            int donationID = Controller.addDonation(donatorID, 12, 0, bloodID);
            assertTrue( "Donation successfully added!", donationID != -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }

    @Test
    public void addDonationWithBadCnp() {
        try {
            int bloodID = Controller.addBlood("ZERO");
            if (bloodID < 0) fail("Blood sample should've been added");

            int donationID = Controller.addDonation("123", 12, 0, 0);
            assertTrue( "Bad donation was prevented from being added!", donationID == -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }
}
