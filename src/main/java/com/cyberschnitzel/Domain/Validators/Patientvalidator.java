package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Patient;


public class Patientvalidator implements Validator<Patient> {
    @Override
    public boolean validate(Patient entity) {
        if (null == entity.getCnp())
            return false;
        if (null == entity.getName())
            return false;
        if (entity.getCnp().length() != 13)
            return false;
        if (!entity.getCnp().matches("[0-9]+"))
            return false;
        return true;
    }
}
