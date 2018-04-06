package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Util.DateUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BloodAdapter implements Adapter<Blood> {
    public BloodAdapter() {
    }

    @Override
    public PreparedStatement saveQuery(Blood entity) throws SQLException {
        String query = "INSERT INTO \"Blood\" (bloodtype) VALUES (?) RETURNING idblood";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getBloodType().name());
        return preparedStatement;
    }

    @Override
    public PreparedStatement deleteQuery(Integer id) throws SQLException {
        String query = "DELETE FROM \"Blood\" WHERE idblood = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement updateQuery(Blood entity) throws SQLException {
        String query = "UPDATE \"Blood\" SET bloodtype = ?, receivedate = ? WHERE idblood = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getBloodType().name());
        preparedStatement.setString(2, DateUtil.getString(entity.getReceivedDate()));
        return preparedStatement;

    }

    @Override
    public PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM \"Blood\" WHERE idblood = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, integer);
        return preparedStatement;
    }

    @Override
    public PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM \"Blood\"";
        return connection.prepareStatement(query);
    }

    @Override
    public Stream<Blood> get(ResultSet rs) {
        List<Blood> bloodList = new ArrayList<>();
        try {
            while (rs.next()) {
                Blood blood = new Blood(rs.getString("bloodtype"));
                blood.setReceivedDate(rs.getDate("receivedate"));
                blood.setId(rs.getInt("idblood"));
                bloodList.add(blood);
            }
        } catch (SQLException sqle) {
            return null;
        }
        return bloodList.stream();
    }
}
