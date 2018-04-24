package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class PatientAdapter implements Adapter<Patient> {
    @Override
    public PreparedStatement saveQuery(Patient entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateQuery(Patient entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        return null;
    }

    @Override
    public Stream<Patient> get(ResultSet rs) {
        return null;
    }
}
