package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Donation;
import com.cyberschnitzel.Domain.Entities.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PatientAdapter implements Adapter<Patient> {
    @Override
    public PreparedStatement saveQuery(Patient entity) throws SQLException {
        String query = "INSERT INTO \"Patient\" (cnp, name) VALUES (?, ?)" +
                "RETURNING id";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Patient\" WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Patient entity) throws SQLException {
        String query = "UPDATE \"Patient\" SET cnp = ?, name = ? WHERE id = ?";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM \"Patient\" WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Patient\"";
        return connection.prepareStatement(query);

    }

    @Override
    public Stream<Patient> get(ResultSet rs) {
        List<Patient> patients = new ArrayList<>();
        try {
            while (rs.next()) {
                Patient patient = new Patient(rs.getString("cnp"), rs.getString("name"));
                patients.add(patient);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return patients.stream();
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Patient entity) throws SQLException {
        preparedStatement.setString(1, entity.getCnp());
        preparedStatement.setString(2, entity.getName());
        if (entity.getId() != null)
            preparedStatement.setInt(3, entity.getId());
        return preparedStatement;
    }
}
