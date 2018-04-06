package com.cyberschnitzel.Domain.Validators;

public interface Validator<T> {
    boolean validate(T entity);
}
