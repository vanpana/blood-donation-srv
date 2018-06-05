package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Used;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UsedAdapter implements Adapter<Used> {

    @Override
    public PreparedStatement saveQuery(Used entity) throws SQLException {
        String query = "INSERT INTO \"Used\" (iddonation, requestid, quantity, bloodparttype) VALUES (?, ?, ?, ?)" +
                " RETURNING idused";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Used\" WHERE idused = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Used entity) throws SQLException {
        String query = "UPDATE \"Used\" SET requestid = ?, quantity = ?, bloodparttype = ? WHERE iddonation = ?";
        PreparedStatement preparedStatement =connection.prepareStatement(query);
        preparedStatement.setInt(1, entity.getRequestId());
        preparedStatement.setFloat(2, entity.getQuantity());
        preparedStatement.setString(3, entity.getBloodPartType());
        preparedStatement.setInt(4, entity.getIdDonation());

        return preparedStatement;
    }

    @Override
    public PreparedStatement findOneQuery(Integer id) throws SQLException {
        String query = "SELECT * FROM \"Used\" WHERE idused = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Used\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Used> get(ResultSet rs) {
        List<Used> useds = new ArrayList<>();
        try {
            while (rs.next()) {
                Used used = new Used(rs.getInt("iddonation"),
                        rs.getInt("requestid"),
                        rs.getFloat("quantity"),
                        rs.getString("bloodparttype"));
                useds.add(used);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return useds.stream();
    }


    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Used entity) throws SQLException {
        preparedStatement.setInt(1, entity.getIdDonation());
        preparedStatement.setInt(2, entity.getRequestId());
        preparedStatement.setFloat(3, entity.getQuantity());
        preparedStatement.setString(4, entity.getBloodPartType());

        return preparedStatement;
    }
}
