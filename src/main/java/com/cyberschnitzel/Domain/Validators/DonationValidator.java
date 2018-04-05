package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Donation;

public class DonationValidator implements Validator<Donation> {
    @Override
    public boolean validate(Donation donation) {
        return donation.getCnp().length() == 10 && donation.getStatus().getStatusID() >= 0 &&
                donation.getStatus().getStatusID() <= 4;
    }
}
