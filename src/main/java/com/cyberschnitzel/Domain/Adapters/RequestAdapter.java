package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.BloodType;
import com.cyberschnitzel.Domain.Entities.Request;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        String query = "DELETE FROM \"Request\" WHERE idrequest = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Request entity) throws SQLException {
        String query = "UPDATE \"Request\" SET quantity = ?, urgency= ?, bloodparttype= ?, locationid= ?, bloodtype= ? WHERE idrequest = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setFloat(1, entity.getQuantity());
        preparedStatement.setInt(2, entity.getUrgency());
        preparedStatement.setString(3, entity.getBloodPartType());
        preparedStatement.setInt(4, entity.getLocation());
        preparedStatement.setString(5, entity.getBloodType().name());
        preparedStatement.setInt(6,entity.getId());
        return preparedStatement;
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
    public Stream<Request> get(ResultSet resultSet) {
        List<Request> requests = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Request request = new Request(resultSet.getFloat("quantity"),
                        resultSet.getInt("urgency"), BloodType.getByString(resultSet.getString("bloodType")),
                        resultSet.getInt("locationId"),resultSet.getString("bloodPartType"));
                request.setId(resultSet.getInt("idrequest"));
                requests.add(request);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return requests.stream();
    }
}

