package com.cyberschnitzel.Repository;

import com.cyberschnitzel.Domain.Adapters.Adapter;
import com.cyberschnitzel.Domain.Entities.Entity;
import com.cyberschnitzel.Domain.Validators.Validator;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class DatabaseRepository<T extends Entity> implements Repository<T> {
    private Validator<T> validator;
    private Adapter<T> adapter;

    public DatabaseRepository(Validator<T> validator, Adapter<T> adapter) {
        this.validator = validator;
        this.adapter = adapter;
    }

    @Override
    public Optional<T> findOne(Integer id) {
        try {
            return Optional.ofNullable(adapter.get(adapter.findOneQuery(id).executeQuery()).collect(Collectors.toList()).get(0));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<T> findAll() {
        try{
            return adapter.get(adapter.findAllQuery().executeQuery()).collect(Collectors.toList());
        } catch (SQLException sqle) {
            return null;
        }
    }

    @Override
    public Optional<T> save(T entity) {
        validator.validate(entity);
        try {
            int id = adapter.saveQuery(entity).executeUpdate();
            entity.setId(id);
            return Optional.of(entity);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> delete(Integer id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        try {
            adapter.deleteQuery(id).execute();
        } catch (SQLException sqle) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) {
        validator.validate(entity);
        try {
            adapter.updateQuery(entity).execute();
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
