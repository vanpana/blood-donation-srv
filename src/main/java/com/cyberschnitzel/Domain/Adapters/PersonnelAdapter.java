package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.sql.PreparedStatement;

public class PersonnelAdapter implements Adapter<Personnel> {

    @Override
    public PreparedStatement saveQuery(Personnel entity) throws SQLException {
        String query = "INSERT INTO \"Personnel\" (name, email, password, token) VALUES (?, ?, ?, ?)" +
                " RETURNING idpersonnel";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Personnel\" WHERE idpersonnel = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Personnel entity) throws SQLException {

        String query = "UPDATE \"Personnel\" SET name = ?, email = ?, password = ?, token = ? WHERE idpersonnel = ?";
        return buildPreparedStatement(connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), entity);
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {

        String query = "SELECT * FROM \"Personnel\" WHERE idpersonnel = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {

        String query = "SELECT * FROM \"Personnel\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Personnel> get(ResultSet resultSet) {
        List<Personnel> personnels = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Personnel personnel = new Personnel(resultSet.getString("name"),resultSet.getString("email"));
                personnel.setId(resultSet.getInt("idpersonnel"));
                personnel.setPassword(resultSet.getString("password"))
                        .setToken(resultSet.getString("token"));
                personnels.add(personnel);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return personnels.stream();
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Personnel entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getEmail());
        preparedStatement.setString(3, entity.getPassword());
        preparedStatement.setString(4, entity.getToken());
        if (entity.getId() != null) preparedStatement.setInt(3, entity.getId());

        return preparedStatement;
    }
}
