package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Used;

public class UsedValidator implements Validator<Used> {
    @Override
    public boolean validate(Used entity) {
        return entity.getPatientCNP().length() == 10 && entity.getIdDonation() > 0 ;
    }
}
