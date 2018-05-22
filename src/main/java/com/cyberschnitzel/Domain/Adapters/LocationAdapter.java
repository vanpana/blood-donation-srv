package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocationAdapter implements Adapter<Location> {
	@Override
	public PreparedStatement saveQuery(Location entity) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement deleteQuery(Integer id) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement updateQuery(Location entity) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement findOneQuery(Integer id) throws SQLException {
		String query = "SELECT * FROM \"Location\" WHERE idlocation = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		return preparedStatement;
	}

	@Override
	public PreparedStatement findAllQuery() throws SQLException {
		String query = "SELECT * FROM \"Location\"";
		return connection.prepareStatement(query);
	}

	@Override
	public Stream<Location> get(ResultSet rs) {
		List<Location> locations = new ArrayList<>();
		try {
			while (rs.next()) {
				Location loc = new Location(rs.getString("name"), rs.getDouble("x"), rs.getDouble("y"), rs.getInt("idlocation"));
				locations.add(loc);
			}
		} catch (SQLException sqle) {
			return null;
		}
		return locations.stream();

	}
}
