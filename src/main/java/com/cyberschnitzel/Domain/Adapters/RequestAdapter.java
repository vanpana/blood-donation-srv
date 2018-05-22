package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Request;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class RequestAdapter implements Adapter<Request> {

    @Override
    public PreparedStatement saveQuery(Request entity) throws SQLException {
        String query = "INSERT INTO \"Request\" (quantity, urgency, bloodparttype, locationid, bloodtype) VALUES (?, ?, ?, ?, ?)" +
                " RETURNING idrequest";

        return buildPreparedStatement(connection.prepareStatement(query),entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateQuery(Request entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM \"Request\" WHERE idrequest = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Request\"";
        return connection.prepareStatement(query);
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Request entity) throws SQLException {
        preparedStatement.setFloat(1, entity.getQuantity());
        preparedStatement.setInt(2, entity.getUrgency());
        preparedStatement.setString(5, entity.getBloodType().name());
        preparedStatement.setInt(4, entity.getLocation());
        preparedStatement.setString(3, entity.getBloodPartType());

        return preparedStatement;
    }

    @Override
    public Stream<Request> get(ResultSet rs) {
        return null;
    }
}
