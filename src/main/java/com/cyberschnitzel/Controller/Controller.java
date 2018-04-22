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
    //=== REPOSITORIES
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

    //=== DONATOR METHODS
    /**
     * Method that adds a donator with the base information.
     * @param cnp - the cnp of the donator
     * @param email - the email of the donator
     * @param name - the name of the donator
     * @return - the id of the added donator
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addDonator(String cnp, String email, String name) throws ControllerException {
        try {
            Optional<Donator> donatorOptional = donatorRepository.save(new Donator(cnp, email, name));
            return donatorOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donator entity: " + e.getMessage());
        }
    }

    /**
     * Method that adds a donator with full information.
     * @param cnp - the cnp of the donator
     * @param email - the email of the donator
     * @param name - the name of the donator
     * @param bloodtype - the bloodtype of the donator (ZERO, A, B or AB)
     * @param password - the password set by the donator
     * @param token - the token which was passed to the donator
     * @return - the id of the added donator
     * @throws ControllerException if the add failed because the data can't be validated
     */
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

    /**
     * Method that deletes a donator by id
     * @param donatorID - the id of the donator to be deleted
     */
    public static void deleteDonator(int donatorID) {
        donatorRepository.delete(donatorID);
    }

    /**
     * Method that updates the base information of a donator
     * @param donatorID - the id of the donator to be deleted
     * @param cnp - the cnp of the donator
     * @param email - the email of the donator
     * @param name - the name of the donator
     * @param bloodtype - the bloodtype of the donator (ZERO, A, B or AB)
     *@throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateDonatorInformation(int donatorID, String cnp, String email, String name, String bloodtype) throws ControllerException {
        Donator donator = new Donator(cnp, email, name).setBloodType(bloodtype);
        donator.setId(donatorID);
        try {
            donatorRepository.update(donator);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update full donator entity: " + e.getMessage());
        }
    }

    /**
     * Method that updates the donator password
     * @param donatorID - the id of the donator
     * @param password - the new password of the donator
     *@throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateDonatorPassword(int donatorID, String password) throws ControllerException {
        Donator donator = getDonatorById(donatorID).setPassword(password);
        try {
        donatorRepository.update(donator);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update donator password: " + e.getMessage());
        }
    }

    /**
     * Method that updates the donator token
     * @param donatorID - the id of the donator
     * @param token - the new password of the donator
     *@throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateDonatorToken(int donatorID, String token) throws ControllerException {
        Donator donator = getDonatorById(donatorID).setToken(token);
        try {
            donatorRepository.update(donator);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update donator token: " + e.getMessage());
        }
    }

    /**
     * Method that returns a donor by id
     * @param donatorID - id of the donator to be fetched
     * @return Donator entity
     */
    public static Donator getDonatorById(int donatorID) {
        return donatorRepository.findOne(donatorID).orElse(null);
    }

    public static Donator getDonatorByEmail(String donatorEmail) {
        for (Donator donator : getAllDonators()) {
            if (donator.getEmail().equals(donatorEmail)) return donator;
        }
        return null;
    }

    /**
     * Method that returns all the donators
     * @return list of donators
     */
    public static List<Donator> getAllDonators() {
        List<Donator> donators = new ArrayList<>();
        donatorRepository.findAll().iterator().forEachRemaining(donators::add);
        return donators;
    }

    //=== BLOOD METHODS
    /**
     * Method that adds a blood sample
     * @param bloodType - the bloodtype of the sample (ZERO, A, B or AB)
     * @return the id of the added bloodtype
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addBlood(String bloodType) throws ControllerException {
        try {
            Optional<Blood> bloodOptional = bloodRepository.save(new Blood(bloodType));
            return bloodOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add blood entity: " + e.getMessage());
        }
    }

    /**
     * Method that deletes a blood sample by id
     * @param bloodID - the id of the donator to be deleted
     */
    public static void deleteBlood(int bloodID) {
        bloodRepository.delete(bloodID);
    }

    /**
     * Method that adds a blood sample by id
     * @param bloodID - the blood id of the sample to be updated
     * @param bloodType - the bloodtype of the sample (ZERO, A, B or AB)
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateBlood(int bloodID,String bloodType) throws ControllerException {
        Blood blood = new Blood(bloodType);
        blood.setId(bloodID);
        try {
            bloodRepository.update(blood);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update blood entity: " + e.getMessage());
        }
    }

    /**
     * Method that gets a blood sample by id
     * @param bloodID - the blood id of the sample to be fetched
     * @return Blood
     */
    public static Blood getBloodByID(int bloodID) {
        return bloodRepository.findOne(bloodID).orElse(null);
    }

    /**
     * Method that gets all blood samples
     * @return List of Blood (samples)
     */
    public static List<Blood> getAllBlood() {
        List<Blood> blood = new ArrayList<>();
        bloodRepository.findAll().iterator().forEachRemaining(blood::add);
        return blood;
    }

    //=== DONATION METHODS
    /**
     * Method that adds a donation by cnp.
     * @param cnp - the cnp of the donator
     * @param quantity - the quantity donated
     * @param status - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID - the id of the blood sample registered
     * @return the id of the added donation
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addDonation(String cnp, double quantity, int status, int bloodID) throws ControllerException {
        try {
            Optional<Donation> donationOptional = donationRepository.save(new Donation(cnp, quantity, status, bloodID));
            return donationOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donation entity: " + e.getMessage());
        }
    }

    /**
     * Method that adds a donation by donatorID.
     * @param donatorID - the donatorID of the donator
     * @param quantity - the quantity donated
     * @param status - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID - the donatorID of the blood sample registered
     * @return the donatorID of the added donation
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addDonation(int donatorID, double quantity, int status, int bloodID) throws ControllerException {
        if (getDonatorById(donatorID) != null) return addDonation(getDonatorById(donatorID).getCnp(), quantity, status, bloodID);
        throw new ControllerException("Failed to add donation entity, no donator with donatorID " + String.valueOf(donatorID));
    }

    /**
     * Method that deletes a donation by id
     * @param donationID - the id of the donation to be deleted
     */
    public static void deleteDonation(int donationID) {
        donationRepository.delete(donationID);
    }

    /**
     * Method that updates a donation.
     * @param donationID - the id of the donation to be updated
     * @param cnp - the cnp of the donator
     * @param quantity - the quantity donated
     * @param status - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID - the id of the blood sample registered
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateDonation(int donationID, String cnp, double quantity, int status, int bloodID) throws ControllerException {
        Donation donation = new Donation(cnp, quantity, status, bloodID);
        donation.setId(donationID);

        try {
            donationRepository.update(donation);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update donation entity: " + e.getMessage());
        }
    }

    /**
     * Method that gets a donations by id
     * @param donationID - the donation id to be fetched
     * @return Donation
     */
    public static Donation getDonationByID(int donationID) {
        Optional<Donation> donationOptional = donationRepository.findOne(donationID);
        return donationOptional.orElse(null);
    }

    /**
     * Method that gets all donations
     * @return List of Donations
     */
    public static List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donationRepository.findAll().iterator().forEachRemaining(donations::add);
        return donations;
    }

    //=== BLOOD PART METHODS
	@SuppressWarnings("unchecked")
    public static List<Blood> getBloodPart(String part) throws ControllerException {
    	try {
			List<Blood> temp = new ArrayList<>();
			Field f = Controller.class.getDeclaredField("bloodParts" + part + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			t.findAll().iterator().forEachRemaining(temp::add);
			return temp;
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static Blood findBloodPart(Class part, Integer id) throws ControllerException {
    	try {
			List<Blood> temp = new ArrayList<>();
			Field f = Controller.class.getDeclaredField("bloodParts" + part.getSimpleName() + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			return t.findOne(id).get();
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
    public static Integer deleteBloodPart(String part, Integer id) throws ControllerException {
		try {
			List<Blood> temp = new ArrayList<>();
			Field f = Controller.class.getDeclaredField("bloodParts" + part + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			t.delete(id);
			return 0;
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage());
		}

	}
	@SuppressWarnings("unchecked")
	public static Integer addBloodPart(Class part, Integer originId, Integer partId, Date date) throws ControllerException {
		try {
			Field f = Controller.class.getDeclaredField("bloodParts" + part.getSimpleName() + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			BloodPart tb =  (BloodPart) part.getConstructor(Integer.class, Integer.class, Date.class).newInstance(partId, originId, date);
			Optional<Blood> ret =  t.save(tb);
			if(!ret.isPresent())
				return 1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public static Integer updateBloodPart(String part, Integer originId, Integer partId, Date date) throws ControllerException {
		try {
			Field f = Controller.class.getDeclaredField("bloodParts" + part + "Repository");
			f.setAccessible(true);
			Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
			t.update(new BloodPart(partId, originId, date));
			return 0;
		} catch (IllegalAccessException | ValidatorException | NoSuchFieldException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage());
		}

	}
}
