package com.cyberschnitzel.Controller;

import com.cyberschnitzel.Domain.Adapters.BloodAdapter;
import com.cyberschnitzel.Domain.Adapters.DonationAdapter;
import com.cyberschnitzel.Domain.Adapters.DonatorAdapter;
import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Domain.Entities.Donation;
import com.cyberschnitzel.Domain.Entities.Donator;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.ValidatorException;
import com.cyberschnitzel.Domain.Validators.BloodValidator;
import com.cyberschnitzel.Domain.Validators.DonationValidator;
import com.cyberschnitzel.Domain.Validators.DonatorValidator;
import com.cyberschnitzel.Repository.DatabaseRepository;
import com.cyberschnitzel.Repository.Repository;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private static Repository<Donator> donatorRepository =
            new DatabaseRepository<>(new DonatorValidator(), new DonatorAdapter());
    private static Repository<Blood> bloodRepository =
            new DatabaseRepository<>(new BloodValidator(), new BloodAdapter());
    private static Repository<Donation> donationRepository =
            new DatabaseRepository<>(new DonationValidator(), new DonationAdapter());

    // Donator
    public static int addDonator(String cnp, String email, String name) throws ControllerException {
        try {
            Optional<Donator> donatorOptional = donatorRepository.save(new Donator(cnp, email, name));
            return donatorOptional.isPresent() ? donatorOptional.get().getId() : -1;
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
            return donatorOptional.isPresent() ? donatorOptional.get().getId() : -1;
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
            return bloodOptional.isPresent() ? bloodOptional.get().getId() : -1;
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add blood entity: " + e.getMessage());
        }
    }

    // TODO: Implement rest of the blood functions

    // Donations
    public static int addDonation(String cnp, double quantity, int status, int bloodID) throws ControllerException {
        try {
            Optional<Donation> donationOptional = donationRepository.save(new Donation(cnp, quantity, status, bloodID));
            return donationOptional.isPresent() ? donationOptional.get().getId() : -1;
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donation entity: " + e.getMessage());
        }
    }

    public static int addDonation(int id, double quantity, int status, int bloodID) throws ControllerException {
        try {
            Optional<Donation> donationOptional = donationRepository.save(new Donation(getDonatorById(id).getCnp(), quantity, status, bloodID));
            return donationOptional.isPresent() ? donationOptional.get().getId() : -1;
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donation entity: " + e.getMessage());
        }
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
}