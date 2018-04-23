package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Personnel;

public class PersonnelValidator implements Validator<Personnel> {
    @Override
    public boolean validate(Personnel entity) {
        return !entity.getName().equals("") && entity.getEmail().contains("@");
    }
}
