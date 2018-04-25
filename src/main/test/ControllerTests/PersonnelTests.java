package ControllerTests;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PersonnelTests {

    private int personnelID;

    @Before
    public void setup() throws ControllerException {
        personnelID = Controller.addPersonnel("Test name", "personnel@email.com");
    }

    @After
    public void teardown() {
        Controller.deleteDonator(personnelID);
    }

    @Test
    public void addCorrectPersonnel() {
        try {
            int personnelID = Controller.addPersonnel("Test2","correct@email.com");
            assertTrue( "Personnel successfully added!", personnelID != -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }

    @Test
    public void addPersonnelBadEmail() {
        try {
            int personnelID = Controller.addPersonnel("Test2","incorrect.email.com");
            assertTrue( "Bad personnel was prevented from being added!!", personnelID == -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }

    @Test
    public void addPersonnelEmptyName() {
        try {
            int personnelID = Controller.addPersonnel("","correct@email.com");
            assertTrue( "Bad personnel was prevented from being added!!", personnelID == -1);
        } catch (ControllerException e) {
            fail("Error should've not been thrown: " + e.getMessage());
        }
    }

}
