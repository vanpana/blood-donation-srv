import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.*;

public class PatientTest {
    private int patientId;

    @Before
    public void setup() throws ControllerException {
        patientId = Controller.addPatient("1234567890123", "Dorinel");
    }

    @After
    public void teardown() {
        Controller.deletePatient(patientId);
    }

    @Test
    public void addCorrectPatient() {
        try {
            int id = 0;
            id = Controller.addPatient("1234567890321", "Teo");
            assertTrue("Patient successfully added!", id != -1);
            Controller.deletePatient(id);
        } catch (ControllerException e) {
            fail();
        }
    }

    @Test
    public void addIncorrectPatient() {
        try {
            int id = 0;
            id = Controller.addPatient("12345678", "Teo");
            assertEquals("Patient validation error", id, -1);
        } catch (ControllerException e) {
            return;
        }
    }

    @Test
    public void removePatient() {

        try {
            int len = Controller.getAllPatients().size();
            int id = 0;
            id = Controller.addPatient("1234567890321", "Teo");
            Controller.deletePatient(id);
            assert (Controller.getAllPatients().size() == len);
        } catch (ControllerException e) {
        }
    }

}