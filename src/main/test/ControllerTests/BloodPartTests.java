package ControllerTests;

import com.cyberschnitzel.Controller.Controller;
import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Domain.Entities.BloodPart;
import com.cyberschnitzel.Domain.Entities.Plasma;
import com.cyberschnitzel.Domain.Entities.RedCells;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BloodPartTests {
	Integer retCode;

	@Before
	public void setup(){
	}

	@After
	public void teardown() throws NoSuchFieldException, IllegalAccessException, ControllerException {
		Controller.deleteBloodPart("Plasma", 100);
	}

	@Test
	public void testAdd() throws NoSuchFieldException, IllegalAccessException, ControllerException {
		try {
			retCode = Controller.addBloodPart(Plasma.class, 1, new SimpleDateFormat("DD.MM.YYYY").parse("01.01.2000"));

		} catch (ParseException e) {
			fail("Date Exception");
			e.printStackTrace();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
		assertTrue("Entity should have been added", retCode == 0);

		Blood bb = Controller.findBloodPart(Plasma.class, 100);
		BloodPart b = (BloodPart)bb;

		assertTrue("Find fail", b.getId() == 100);
	}

	@Test
	public void testUpdate() throws NoSuchFieldException, IllegalAccessException, ParseException, ControllerException {
		try {
			Date d = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
			retCode = Controller.addBloodPart(Plasma.class, 1, d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {

			retCode = Controller.updateBloodPart("Plasma",1,100, new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		Blood bb = Controller.findBloodPart(Plasma.class, 100);
		BloodPart b = (BloodPart)bb;

		Long result = new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000").getTime();
		Long updateResult = b.getExpirationDate().getTime();
		assertTrue("Find fail", b.getExpirationDate().compareTo(new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000"))==0);


		try {
			Date d = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
			retCode = Controller.addBloodPart(RedCells.class, 1, d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {

			retCode = Controller.updateBloodPart("RedCells",1,100, new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bb = Controller.findBloodPart(RedCells.class, 100);
		b = (BloodPart)bb;

		result = new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000").getTime();
		updateResult = b.getExpirationDate().getTime();
		assertTrue("Find fail", b.getExpirationDate().compareTo(new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2000"))==0);



	}

}

