package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Doctor;

public class DoctorValidator implements Validator<Doctor> {

    @Override
    public boolean validate(Doctor entity) {
        return !entity.getName().equals("") && entity.getEmail().contains("@");
    }
}
