package com.cyberschnitzel.Domain.Validators;

import com.cyberschnitzel.Domain.Entities.Blood;

public class BloodValidator implements Validator<Blood> {
    @Override
    public boolean validate(Blood entity) {
        // TODO: Better validate the entity
        return entity.getBloodType() != null;
    }
}
