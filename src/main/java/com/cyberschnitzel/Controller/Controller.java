package com.cyberschnitzel.Controller;

import com.cyberschnitzel.Domain.Adapters.BloodAdapter;
import com.cyberschnitzel.Domain.Adapters.BloodPartAdapter;
import com.cyberschnitzel.Domain.Adapters.DonationAdapter;
import com.cyberschnitzel.Domain.Adapters.DonatorAdapter;
import com.cyberschnitzel.Domain.Entities.*;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.ValidatorException;
import com.cyberschnitzel.Domain.Validators.BloodValidator;
import com.cyberschnitzel.Domain.Validators.DonationValidator;
import com.cyberschnitzel.Domain.Validators.DonatorValidator;
import com.cyberschnitzel.Repository.DatabaseRepository;
import com.cyberschnitzel.Repository.Repository;
import com.cyberschnitzel.Domain.Entities.Plasma;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Controller {
    private static Repository<Donator> donatorRepository =
            new DatabaseRepository<>(new DonatorValidator(), new DonatorAdapter());
    private static Repository<Blood> bloodRepository =
            new DatabaseRepository<>(new BloodValidator(), new BloodAdapter());
    private static Repository<Donation> donationRepository =
            new DatabaseRepository<>(new DonationValidator(), new DonationAdapter());
    private static Repository<Blood> bloodPartsPlasmaRepository =
			new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("Plasma"));
	private static Repository<Blood> bloodPartsRedCellsRepository =
			new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("RedCells"));
	private static Repository<Blood> bloodPartsThrombocitesRepository =
			new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("Thrombocites"));

	public static void reinitRepositories(){
		Repository<Donator> donatorRepository =
				new DatabaseRepository<>(new DonatorValidator(), new DonatorAdapter());
		Repository<Blood> bloodRepository =
				new DatabaseRepository<>(new BloodValidator(), new BloodAdapter());
		Repository<Donation> donationRepository =
				new DatabaseRepository<>(new DonationValidator(), new DonationAdapter());
		Repository<Blood> bloodPartsPlasmaRepository =
				new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("Plasma"));
		Repository<Blood> bloodPartsRedCellsRepository =
				new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("RedCells"));
		Repository<Blood> bloodPartsThrombocitesRepository =
				new DatabaseRepository<>(new BloodValidator(), new BloodPartAdapter("Thrombocites"));

	}

    // Donator
    public static int addDonator(String cnp, String email, String name) throws ControllerException {
        try {
            Optional<Donator> donatorOptional = donatorRepository.save(new Donator(cnp, email, name));
            return donatorOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donator entity: " + e.getMessage());
        }
    }

    public static int addDonator(String cnp, String email, String name, String bloodtype, String password, String token) throws ControllerException {
        try {
            Optional<Donator> donatorOptional = donatorRepository.save(
                    new Donator(cnp, email, name).setBloodType(bloodtype)
                            .setPassword(password)
                            .setToken(token));
            return donatorOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donator entity: " + e.getMessage());
        }
    }

    public static void deleteDonator(int donationID) {
        donatorRepository.delete(donationID);
    }


    public static Donator getDonatorById(int id) {
        return donatorRepository.findOne(id).orElse(null);
    }

    // TODO: Implement rest of the donator functions

    // Blood
    public static int addBlood(String bloodType) throws ControllerException {
        try {
            Optional<Blood> bloodOptional = bloodRepository.save(new Blood(bloodType));
            return bloodOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add blood entity: " + e.getMessage());
        }
    }

    // TODO: Implement rest of the blood functions

    // Donations
    public static int addDonation(String cnp, double quantity, int status, int bloodID) throws ControllerException {
        try {
            Optional<Donation> donationOptional = donationRepository.save(new Donation(cnp, quantity, status, bloodID));
            return donationOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donation entity: " + e.getMessage());
        }
    }

    public static int addDonation(int id, double quantity, int status, int bloodID) throws ControllerException {
        if (getDonatorById(id) != null) return addDonation(getDonatorById(id).getCnp(), quantity, status, bloodID);
        throw new ControllerException("Failed to add donation entity, no donator with id " + String.valueOf(id));
    }

    public static void deleteDonation(int donationID) {
        donationRepository.delete(donationID);
    }

    public static void updateDonation(int donationID, String cnp, double quantity, int status, int bloodID) throws ControllerException {
        Donation donation = new Donation(cnp, quantity, status, bloodID);
        donation.setId(donationID);

        try {
            donationRepository.update(donation);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update donation entity: " + e.getMessage());
        }
    }

    public static Donation getDonationByID(int donationID) {
        Optional<Donation> donationOptional = donationRepository.findOne(donationID);
        return donationOptional.orElse(null);
    }

    public static List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donationRepository.findAll().iterator().forEachRemaining(donations::add);
        return donations;
    }

    public static List<Blood> getBloodPart(String part) throws NoSuchFieldException, IllegalAccessException {
		List<Blood> temp = new ArrayList<>();
		Field f = Controller.class.getDeclaredField("bloodParts"+part+"Repository");
		f.setAccessible(true);
		Repository<Blood> t = (Repository<Blood>)f.get(Controller.class);
		t.findAll().iterator().forEachRemaining(temp::add);
		return temp;
    }


	public static Blood findBloodPart(Class part, Integer id) throws NoSuchFieldException, IllegalAccessException {
		List<Blood> temp = new ArrayList<>();
		Field f = Controller.class.getDeclaredField("bloodParts"+part.getSimpleName()+"Repository");
		f.setAccessible(true);
		Repository<Blood> t = (Repository<Blood>)f.get(Controller.class);
		return t.findOne(id).get();

	}

    public static Integer deleteBloodPart(String part, Integer id) throws NoSuchFieldException, IllegalAccessException {
		List<Blood> temp = new ArrayList<>();
		Field f = Controller.class.getDeclaredField("bloodParts"+part+"Repository");
		f.setAccessible(true);
		Repository<Blood> t = (Repository<Blood>)f.get(Controller.class);
		t.delete(id);
		return 0;
	}

	public static Integer addBloodPart(Class part, Integer originId, Integer partId, Date date) throws  ValidatorException {
		try {
			Field f = Controller.class.getDeclaredField("bloodParts" + part.getSimpleName() + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);


			BloodPart tb =  (BloodPart)part.getConstructor(Integer.class, Integer.class, Date.class).newInstance(partId, originId, date);


			Optional<Blood> ret =  t.save(tb);
			if(!ret.isPresent())
				return 1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	public static Integer updateBloodPart(String part, Integer originId, Integer partId, Date date) throws  ValidatorException {
		try {
			Field f = Controller.class.getDeclaredField("bloodParts" + part + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			t.update(new BloodPart(partId, originId, date));
			return 0;
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
			return 1;
		}
	}

}
