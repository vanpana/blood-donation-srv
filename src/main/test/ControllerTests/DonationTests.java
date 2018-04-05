package ControllerTests;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DonationTests {
    @Test
    public void addCorrectDonation() {
        try {
            int bloodID = Controller.addBlood("ZERO");
            if (bloodID < 0) fail("Blood sample should've been added");

            int donationID = Controller.addDonation("1234567890", 12, 0, 0);
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

            Controller.addDonation("123", 12, 0, 0);
            fail();
        } catch (ControllerException e) {
            assertTrue( "Bad donation was prevented from being added!", true);
        }
    }
}
