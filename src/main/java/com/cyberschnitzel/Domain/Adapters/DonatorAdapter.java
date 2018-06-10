package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Donator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DonatorAdapter implements Adapter<Donator> {
    @Override
    public PreparedStatement saveQuery(Donator entity) throws SQLException {
        String query = "INSERT INTO \"Donator\" (cnp, name, bloodtype, email, password, token, location) VALUES (?, ?, ?, ?, ?, ?, ?)" +
                " RETURNING iddonator";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Donator\" WHERE iddonator = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Donator entity) throws SQLException {
        String query = "UPDATE \"Donator\" SET cnp = ?, name = ?, bloodtype = ?, email = ?, password = ?, token = ?, location =? WHERE iddonator = ?";
        return buildPreparedStatement(connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), entity);
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM \"Donator\" WHERE iddonator = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Donator\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Donator> get(ResultSet resultSet) {
        List<Donator> donators = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Donator donator = new Donator(resultSet.getString("cnp"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),resultSet.getString("location"));
                donator.setId(resultSet.getInt("iddonator"));
                donator.setBloodType(resultSet.getString("bloodtype"))
                        .setPassword(resultSet.getString("password"))
                        .setToken(resultSet.getString("token"));
                donators.add(donator);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return donators.stream();
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Donator entity) throws SQLException {
        preparedStatement.setString(1, entity.getCnp());
        preparedStatement.setString(2, entity.getName());

        if (entity.getBloodtype() != null) preparedStatement.setString(3, entity.getBloodtype().name());
        else preparedStatement.setString(3, "");
        preparedStatement.setString(4, entity.getEmail());
        preparedStatement.setString(5, entity.getPassword());
        preparedStatement.setString(6, entity.getToken());
        preparedStatement.setString(7, entity.getLocation());
        if (entity.getId() != null) preparedStatement.setInt(8, entity.getId());

        return preparedStatement;
    }
}
