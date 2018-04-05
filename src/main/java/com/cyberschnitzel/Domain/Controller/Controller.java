package com.cyberschnitzel.Domain.Controller;

import com.cyberschnitzel.Domain.Adapters.DonationAdapter;
import com.cyberschnitzel.Domain.Entities.Donation;
import com.cyberschnitzel.Domain.Exceptions.ControllerException;
import com.cyberschnitzel.Domain.Exceptions.ValidatorException;
import com.cyberschnitzel.Domain.Validators.DonationValidator;
import com.cyberschnitzel.Repository.DatabaseRepository;
import com.cyberschnitzel.Repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private static Repository<Integer, Donation> donationRepository =
            new DatabaseRepository<>(new DonationValidator(), new DonationAdapter());

    // Donations
    public void addDonation(String cnp, double quantity, int status, int bloodID) throws ControllerException {
        try {
            donationRepository.save(new Donation(cnp, quantity, status, bloodID));
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to add donation entity: " + e.getMessage());
        }
    }

    public void deleteDonation(int donationID) {
        donationRepository.delete(donationID);
    }

    public void updateDonation(int donationID, String cnp, double quantity, int status, int bloodID) throws ControllerException {
        Donation donation = new Donation(cnp, quantity, status, bloodID);
        donation.setId(donationID);

        try {
            donationRepository.update(donation);
        } catch (ValidatorException e) {
            throw new ControllerException("Failed to update donation entity: " + e.getMessage());
        }
    }

    public Donation getDonationByID(int donationID) {
        Optional<Donation> donationOptional = donationRepository.findOne(donationID);
        return donationOptional.orElse(null);
    }

    public List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donationRepository.findAll().iterator().forEachRemaining(donations::add);
        return donations;
    }
}
