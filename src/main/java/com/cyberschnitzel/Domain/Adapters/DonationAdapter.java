package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Donation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DonationAdapter implements Adapter<Donation> {
    public DonationAdapter() {
    }

    @Override
    public PreparedStatement saveQuery(Donation entity) throws SQLException {
        String query = "INSERT INTO \"Donation\" (cnp, quantity, status, idblood) VALUES (?, ?, ?, ?)" +
                " RETURNING iddonation";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Donation\" WHERE iddonation = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Donation entity) throws SQLException {
        String query = "UPDATE \"Donation\" SET cnp = ?, quantity = ?, status = ?, idblood = ? WHERE iddonation = ?";
        return buildPreparedStatement(connection.prepareStatement(query), entity);
    }

    @Override
    public PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM \"Donation\" WHERE iddonation = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, integer);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Donation\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Donation> get(ResultSet rs) {
        List<Donation> donations = new ArrayList<>();
        try {
            while (rs.next()) {
                Donation donation = new Donation(rs.getString("cnp"),
                        rs.getDouble("quantity"), rs.getInt("status"), rs.getInt("idblood"));
                donation.setId(rs.getInt("iddonation"));
                donations.add(donation);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return donations.stream();
    }

    private PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Donation entity) throws SQLException {
        preparedStatement.setString(1, entity.getCnp());
        preparedStatement.setDouble(2, entity.getQuantity());
        preparedStatement.setInt(3, entity.getStatus().getStatusID());
        preparedStatement.setInt(4, entity.getBloodID());

        if (entity.getId() != null)
            preparedStatement.setInt(5, entity.getId());
        return preparedStatement;
    }
}
