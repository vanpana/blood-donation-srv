package com.cyberschnitzel.Controller;

import com.cyberschnitzel.Domain.Adapters.*;
import com.cyberschnitzel.Domain.Entities.*;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.ValidatorException;
import com.cyberschnitzel.Domain.Validators.*;
import com.cyberschnitzel.Repository.DatabaseRepository;
import com.cyberschnitzel.Repository.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Controller {
    //<editor-fold desc="Repositories">
    private static Repository<Donator> donatorRepository =
            new DatabaseRepository<>(new DonatorValidator(), new DonatorAdapter());
    private static Repository<Blood> bloodRepository =
            new DatabaseRepository<>(new BloodValidator(), new BloodAdapter());
    private static Repository<Donation> donationRepository =
            new DatabaseRepository<>(new DonationValidator(), new DonationAdapter());
    private static Repository<BloodPart> bloodPartsPlasmaRepository =
            new DatabaseRepository<>(new BloodPartValidator(), new BloodPartAdapter("Plasma"));
    private static Repository<BloodPart> bloodPartsRedCellsRepository =
            new DatabaseRepository<>(new BloodPartValidator(), new BloodPartAdapter("RedCells"));
    private static Repository<BloodPart> bloodPartsThrombocitesRepository =
            new DatabaseRepository<>(new BloodPartValidator(), new BloodPartAdapter("Thrombocites"));
    private static Repository<Personnel> personnelRepository =
            new DatabaseRepository<>(new PersonnelValidator(), new PersonnelAdapter());
    private static Repository<Patient> patientRepository =
            new DatabaseRepository<>(new Patientvalidator(), new PatientAdapter());
    private static Repository<Used> usedRepository =
            new DatabaseRepository<>(new UsedValidator(), new UsedAdapter());
    private static Repository<Location> locationRepository = new DatabaseRepository<>(new LocationValidator(), new LocationAdapter());
    private static Repository<Doctor> doctorRepository =
            new DatabaseRepository<>(new DoctorValidator(), new DoctorAdapter());
    private static Repository<Request> requestRepository= new DatabaseRepository<>(new RequestValidator(), new RequestAdapter());

    //</editor-fold>

    //<editor-fold desc="Donator methods">

    /**
     * Method that adds a donator with the base information.
     *
     * @param cnp   - the cnp of the donator
     * @param email - the email of the donator
     * @param name  - the name of the donator
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
     *
     * @param cnp       - the cnp of the donator
     * @param email     - the email of the donator
     * @param name      - the name of the donator
     * @param bloodtype - the bloodtype of the donator (ZERO, A, B or AB)
     * @param password  - the password set by the donator
     * @param token     - the token which was passed to the donator
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
     *
     * @param donatorID - the id of the donator to be deleted
     */
    public static void deleteDonator(int donatorID) {
        donatorRepository.delete(donatorID);
    }

    /**
     * Method that updates the base information of a donator
     *
     * @param donatorID - the id of the donator to be deleted
     * @param cnp       - the cnp of the donator
     * @param email     - the email of the donator
     * @param name      - the name of the donator
     * @param bloodtype - the bloodtype of the donator (ZERO, A, B or AB)
     * @throws ControllerException if the add failed because the data can't be validated
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
     *
     * @param donatorID - the id of the donator
     * @param password  - the new password of the donator
     * @throws ControllerException if the add failed because the data can't be validated
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
     *
     * @param donatorID - the id of the donator
     * @param token     - the new password of the donator
     * @throws ControllerException if the add failed because the data can't be validated
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
     *
     * @param donatorID - id of the donator to be fetched
     * @return Donator entity
     */
    private static Donator getDonatorById(int donatorID) {
        return donatorRepository.findOne(donatorID).orElse(null);
    }

    /**
     * Method that returns a donor by email
     *
     * @param donatorEmail - email of the donator to be fetched
     * @return Donator entity
     */
    public static Donator getDonatorByEmail(String donatorEmail) {
        for (Donator donator : getAllDonators()) {
            if (donator.getEmail().equals(donatorEmail)) return donator;
        }
        return null;
    }

    /**
     * Method that returns a donor by cnp
     *
     * @param donatorCnp - cnp of the donator to be fetched
     * @return Donator entity
     */
    public static Donator getDonatorByCnp(String donatorCnp) {
        for (Donator donator : getAllDonators()) {
            if (donator.getCnp().equals(donatorCnp)) return donator;
        }
        return null;
    }

    /**
     * Method that returns all the donators
     *
     * @return list of donators
     */
    private static List<Donator> getAllDonators() {
        List<Donator> donators = new ArrayList<>();
        donatorRepository.findAll().iterator().forEachRemaining(donators::add);
        return donators;
    }

    //</editor-fold>

    //<editor-fold desc="Blood methods">

    /**
     * Method that adds a blood sample
     *
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
     *
     * @param bloodID - the id of the donator to be deleted
     */
    public static void deleteBlood(int bloodID) {
        bloodRepository.delete(bloodID);
    }

    /**
     * Method that adds a blood sample by id
     *
     * @param bloodID   - the blood id of the sample to be updated
     * @param bloodType - the bloodtype of the sample (ZERO, A, B or AB)
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static void updateBlood(int bloodID, String bloodType) throws ControllerException {
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
     *
     * @param bloodID - the blood id of the sample to be fetched
     * @return Blood
     */
    public static Blood getBloodByID(int bloodID) {
        return bloodRepository.findOne(bloodID).orElse(null);
    }

    /**
     * Method that gets all blood samples
     *
     * @return List of Blood (samples)
     */
    public static List<Blood> getAllBlood() {
        List<Blood> blood = new ArrayList<>();
        bloodRepository.findAll().iterator().forEachRemaining(blood::add);
        return blood;
    }
    //</editor-fold>

    //<editor-fold desc="Donation methods">

    /**
     * Method that adds a donation by cnp.
     *
     * @param cnp      - the cnp of the donator
     * @param quantity - the quantity donated
     * @param status   - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID  - the id of the blood sample registered
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
     *
     * @param donatorID - the donatorID of the donator
     * @param quantity  - the quantity donated
     * @param status    - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID   - the donatorID of the blood sample registered
     * @return the donatorID of the added donation
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addDonation(int donatorID, double quantity, int status, int bloodID) throws ControllerException {
        if (getDonatorById(donatorID) != null)
            return addDonation(getDonatorById(donatorID).getCnp(), quantity, status, bloodID);
        throw new ControllerException("Failed to add donation entity, no donator with donatorID " + String.valueOf(donatorID));
    }

    /**
     * Method that deletes a donation by id
     *
     * @param donationID - the id of the donation to be deleted
     */
    public static void deleteDonation(int donationID) {
        donationRepository.delete(donationID);
    }

    /**
     * Method that updates a donation.
     *
     * @param donationID - the id of the donation to be updated
     * @param cnp        - the cnp of the donator
     * @param quantity   - the quantity donated
     * @param status     - the status of the donation (0 most probably) - 0 = Collected, 1 = Testing, 2 = Approved, 3 = Declined
     * @param bloodID    - the id of the blood sample registered
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
     *
     * @param donationID - the donation id to be fetched
     * @return Donation
     */
    public static Donation getDonationByID(int donationID) {
        Optional<Donation> donationOptional = donationRepository.findOne(donationID);
        return donationOptional.orElse(null);
    }

    /**
     * Method that gets all donations
     *
     * @return List of Donations
     */
    public static List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donationRepository.findAll().iterator().forEachRemaining(donations::add);
        return donations;
    }
    //</editor-fold>

    //<editor-fold desc="Blood part methods">
    @SuppressWarnings("unchecked")
    public static List<BloodPart> getBloodPart(String part) throws ControllerException {
        try {
            List<BloodPart> temp = new ArrayList<>();
            Field f = Controller.class.getDeclaredField("bloodParts" + part + "Repository");
            f.setAccessible(true);
            Repository<BloodPart> t = (Repository<BloodPart>) f.get(Controller.class);
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
            Field f = Controller.class.getDeclaredField("bloodParts" + part.getSimpleName() + "Repository");
            f.setAccessible(true);
            Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
            return t.findOne(id).orElse(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Integer deleteBloodPart(String part, Integer id) throws ControllerException {
        try {
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
    public static Integer addBloodPart(Class part, Integer originId, Date date) throws ControllerException {
        try {
            Field f = Controller.class.getDeclaredField("bloodParts" + part.getSimpleName() + "Repository");
            f.setAccessible(true);
            Repository<Blood> t = (Repository<Blood>) f.get(Controller.class);
            BloodPart tb = (BloodPart) part.getConstructor(Integer.class, Date.class).newInstance(originId, date);
            tb.setReceivedDate(date);
            Optional<Blood> ret = t.save(tb);
			return ret.map(Entity::getId).orElse(-1);
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
            Repository<BloodPart> t = (Repository<BloodPart>) f.get(Controller.class);
            t.update(new BloodPart(partId, originId, date));
            return 0;
        } catch (IllegalAccessException | ValidatorException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new ControllerException(e.getMessage());
        }

    }
    //</editor-fold>

    //<editor-fold desc="Personnel methods">

    /**
     * Method that adds a personnel with the full information.
     *
     * @param email - the email of the personnel
     * @param name  - the name of the personnel
     * @return - the id of the added personnel
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static int addPersonnel(String name, String email) throws ControllerException {
        try {
            Optional<Personnel> personnelOptional = personnelRepository.save(new Personnel(name, email));
            return personnelOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add personnel entity: " + e.getMessage());
        }
    }

    public static void updatePersonnelToken(int personnelID, String token) throws ControllerException {
        Personnel pers = getPersonnelByID(personnelID).setToken(token);
        try {
            personnelRepository.update(pers);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update pers token: " + e.getMessage());
        }
    }

    /**
     * Method that deletes a personnel by id
     *
     * @param personnelID - the id of the personnel to be deleted
     */
    public static void deletePersonnel(int personnelID) {
        personnelRepository.delete(personnelID);
    }


    /**
     * Method that updates the information of a personnel
     *
     * @param personnelID - the id of the personnel to be deleted
     * @param name        - the name of the personnel
     * @param email       - the email of the personnel
     * @throws ControllerException if the add failed because the data can't be validated
     */
    public static void updatePersonnelInformation(int personnelID, String name, String email) throws ControllerException {
        Personnel personnel = new Personnel(name, email);
        personnel.setId(personnelID);
        try {
            personnelRepository.update(personnel);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update full personnel entity: " + e.getMessage());
        }
    }

    /**
     * Method that gets a personnel by id
     *
     * @param personnelID - the personnel id to be fetched
     * @return Personnel
     */
    public static Personnel getPersonnelByID(int personnelID) {
        Optional<Personnel> personnelOptional = personnelRepository.findOne(personnelID);
        return personnelOptional.orElse(null);
    }

    /**
     * Method that gets a personnel by email
     *
     * @param personnelEmail - the personnel email to be fetched
     * @return Personnel
     */
    public static Personnel getPersonnelByEmail(String personnelEmail) {
        for (Personnel personnel : getAllPersonnel()) {
            if (personnel.getEmail().equals(personnelEmail)) return personnel;
        }
        return null;
    }

    /**
     * Method that gets all personnel
     *
     * @return List of Personnel
     */
    private static List<Personnel> getAllPersonnel() {
        List<Personnel> personnels = new ArrayList<>();
        personnelRepository.findAll().iterator().forEachRemaining(personnels::add);
        return personnels;
    }
    //</editor-fold>

    //<editor-fold desc="Used methods">

    /**
     * Method that adds a used quantity for a specific donation having as a target the patient with a given cnp
     *
     * @param idDontaion - the id of the used
     * @param patientCNP - the cnp of the patient to whom the quantity will go
     * @param quantity   - the quantity of blood used from a given donation
     * @return - the id of the donation
     * @throws ControllerException -if any exception occur
     */
    public static int addUsed(int idDontaion, String patientCNP, float quantity) throws ControllerException {
        try {
            Used used = new Used(idDontaion, patientCNP, quantity);
            Optional<Used> usedOptional = usedRepository.save(used);
            return usedOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add a used entity, no donator with donatorID " + idDontaion + "\n" + e.getMessage());
        }
    }

    /**
     * Method deletes an used registration by id
     *
     * @param idDonation -the id of the deleted used
     */
    public static void deleteUsed(int idDonation) {
        usedRepository.delete(idDonation);
    }

    /**
     * Method that updates a used entity
     *
     * @param idDonation - the id donation of the used entity to be updated
     * @param patientCNP - the new patientCNP
     * @param quantity   - the new quantity
     * @throws ControllerException - if the update cannot be done
     */
    public static void updateUsed(int idDonation, String patientCNP, float quantity) throws ControllerException {
        Used used = new Used(idDonation, patientCNP, quantity);
        try {
            usedRepository.update(used);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update a used entity" + e.getMessage());
        }
    }

    /**
     * Method that return a used entity by id
     *
     * @param idDonation - the id of the used entity to be found
     * @return - an used entity
     */
    public static Used getUsedById(int idDonation) {
        Optional<Used> usedOptional = usedRepository.findOne(idDonation);
        return usedOptional.orElse(null);
    }

    /**
     * Method that returns the list of all used entities
     *
     * @return - a list of used entities
     */
    public static List<Used> getAllUsed() {
        List<Used> usedList = new ArrayList<>();
        usedRepository.findAll().iterator().forEachRemaining(usedList::add);
        return usedList;
    }
    //</editor-fold>

    //<editor-fold desc="Patient methods">
    public static int addPatient(String cnp, String name) throws ControllerException {
        try {
            Optional<Patient> patientOptional = patientRepository.save(new Patient(cnp, name));
            return patientOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add patient");
        }
    }

    public static void deletePatient(int patientID) {
        patientRepository.delete(patientID);
    }

    public static void updatePatient(int patientID, String cnp, String name) throws ControllerException {
        Patient patient = new Patient(cnp, name);
        patient.setId(patientID);
        try {
            patientRepository.update(patient);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update patient");
        }
    }

    public static Patient getPatientById(int patientID) {
        Optional<Patient> patientOptional = patientRepository.findOne(patientID);
        return patientOptional.orElse(null);
    }

    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patientRepository.findAll().iterator().forEachRemaining(patients::add);
        return patients;
    }
    //</editor-fold>

    //<editor-fold desc="Utils methods">

    public static boolean checkCredentialsNoToken(String email, String password, CredentialsEntity.EntityType entityType) throws ControllerException {
        CredentialsEntity credentialsEntity = getCredentialToBeValidated(entityType, email);

        // Hash the password and the token
//
//		try {
//			password = Hasher.encrypt(password);
//		} catch (HashingException he) {
//			throw new ControllerException("Failed to hash password or token: " + he.getMessage());
//		}

        // Check if the password and token match the database ones
        if (!password.equals(credentialsEntity.getPassword()))
            throw new ControllerException("Incorrect password");
        return true;
    }

    public static boolean checkCredentials(String email, String password, String token, CredentialsEntity.EntityType entityType) throws ControllerException {
        CredentialsEntity credentialsEntity = getCredentialToBeValidated(entityType, email);

        /*
        // Hash the password and the token
        try {
            password = Hasher.encrypt(password);
            token = Hasher.encrypt(token);
        } catch (HashingException he) {
            throw new ControllerException("Failed to hash password or token: " + he.getMessage());
        }
        */

        // Check if the password and token match the database ones
        if (!password.equals(credentialsEntity.getPassword()))
            throw new ControllerException("Incorrect password");
        if (!token.equals(credentialsEntity.getToken())) throw new ControllerException("Incorrect token");

        return true;
    }

    private static CredentialsEntity getCredentialToBeValidated(CredentialsEntity.EntityType entityType, String email) throws ControllerException{
        CredentialsEntity credentialsEntity = null;

        // Try to fetch the entity with specified mail
        if (entityType == CredentialsEntity.EntityType.DONATOR) credentialsEntity = getDonatorByEmail(email);
        else if (entityType == CredentialsEntity.EntityType.PERSONNEL) credentialsEntity = getPersonnelByEmail(email);
        else if (entityType == CredentialsEntity.EntityType.DOCTOR) credentialsEntity = getDoctorByEmail(email);

        // Check if the entity exists
        if (credentialsEntity == null) {
            if (entityType == CredentialsEntity.EntityType.DONATOR)
                throw new ControllerException("Inexistent donor with specified email.");
            else if (entityType == CredentialsEntity.EntityType.PERSONNEL)
                throw new ControllerException("Inexistent personnel with specified email.");
            else if (entityType == CredentialsEntity.EntityType.DOCTOR)
                throw new ControllerException("Inexistent doctor with specified email");
            else throw new ControllerException("Inexistent user with specified email");
        }

        // Check if the entity exists
        if (credentialsEntity == null) {
            if (entityType == CredentialsEntity.EntityType.DONATOR)
                throw new ControllerException("Inexistent donor with specified email.");
            else if (entityType == CredentialsEntity.EntityType.PERSONNEL)
                throw new ControllerException("Inexistent personnel with specified email.");
            else if (entityType == CredentialsEntity.EntityType.DOCTOR)
                throw new ControllerException("Inexistent doctor with specified email");
            else throw new ControllerException("Inexistent user with specified email");
        }
        return credentialsEntity;
    }

    //</editor-fold>

    public static Location getLocationById(Integer id)
    {
        Optional<Location> opt = locationRepository.findOne(id);
        return opt.orElse(null);
    }

    //<editor-fold desc = "Doctor methods">

    //<editor-fold desc = "Doctor methods">


    public static int addDoctor(String name, String email) throws ControllerException {
        try {
            Optional<Doctor> doctorOptional = doctorRepository.save(new Doctor(name, email));
            return doctorOptional.map(Entity::getId).orElse(-1);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add personnel entity: " + e.getMessage());
        }
    }

    public static void updateDoctorToken(int iddoctor, String token) throws ControllerException {
        Doctor doctor = getDoctorById(iddoctor).setToken(token);
        try {
            doctorRepository.update(doctor);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update pers token: " + e.getMessage());
        }
    }

    public static void deleteDoctor(int iddoctor){
        doctorRepository.delete(iddoctor);
    }

    public static Doctor getDoctorById(int iddoctor) {
        Optional<Doctor> doctorOptional = doctorRepository.findOne(iddoctor);
        return doctorOptional.orElse(null);
    }

    public static void updateDoctorInformation(int iddoctor, String name, String email) throws ControllerException {
        Doctor doctor = new Doctor(name, email);
        doctor.setId(iddoctor);
        try {
            doctorRepository.update(doctor);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update full doctor entity: " + e.getMessage());
        }
    }

    public static Doctor getDoctorByEmail(String doctorEmail) {
        for (Doctor doctor : getAllDoctors()) {
            if (doctor.getEmail().equals(doctorEmail)) return doctor;
        }
        return null;
    }


    private static List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctorRepository.findAll().iterator().forEachRemaining(doctors::add);
        return doctors;
    }

//</editor-fold>
    public static List<Request> getAllRequests()
    {
        List<Request> requests = new ArrayList<>();
        requestRepository.findAll().iterator().forEachRemaining(requests::add);
        return requests;
    }

}
