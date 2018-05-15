package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DoctorAdapter implements Adapter<Doctor> {
    @Override
    public PreparedStatement saveQuery(Doctor entity) throws SQLException {
        String query = "INSERT INTO \"Doctor\" (name, email, password, token) VALUES (?, ?, ?, ?)" +
                " RETURNING iddoctor";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Doctor\" WHERE iddoctor = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Doctor entity) throws SQLException {
        String query = "UPDATE \"Doctor\" SET name = ?, email = ?, password = ?, token = ? WHERE iddoctor = ?";
        return buildPreparedStatement(connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), entity);
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM \"Doctor\" WHERE iddoctor = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Doctor\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Doctor> get(ResultSet resultSet) {
        List<Doctor> doctors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Doctor doctor = new Doctor( resultSet.getString("email"),resultSet.getString("name"));
                doctor.setId(resultSet.getInt("iddoctor"));
                doctor.setPassword(resultSet.getString("password"))
                        .setToken(resultSet.getString("token"));
                doctors.add(doctor);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return doctors.stream();
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Doctor entity) throws SQLException {
        preparedStatement.setString(1,entity.getName());
        preparedStatement.setString(2, entity.getEmail());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getToken());
        if (entity.getId() != null) preparedStatement.setInt(5, entity.getId());
            return preparedStatement;
    }
}
