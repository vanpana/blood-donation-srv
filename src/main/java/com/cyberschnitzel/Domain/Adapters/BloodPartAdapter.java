package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.Blood;
import com.cyberschnitzel.Domain.Entities.BloodPart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BloodPartAdapter<T extends BloodPart> implements Adapter<Blood> {

	private String tableName;


	public BloodPartAdapter(String partName )
	{
		tableName = partName;
	}
	@Override
	public PreparedStatement saveQuery(Blood entity) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement deleteQuery(Integer id) throws SQLException {
		String query = "DELETE FROM " + "\"" + tableName + "\"" + " WHERE id" + tableName.toLowerCase() + " = ?";
		PreparedStatement p = connection.prepareStatement(query);
		p.setInt(1,id);
		return p;
	}

	@Override
	public PreparedStatement updateQuery(Blood entity) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement findOneQuery(Integer id) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement findAllQuery() throws SQLException {
		String query = "SELECT * FROM " + "\"" + tableName + "\"";
		return connection.prepareStatement(query);
	}

	@Override
	public Stream<Blood> get(ResultSet rs) {
		List<Blood> bloodList = new ArrayList<>();
		try {
			while (rs.next()) {
				BloodPart blood = new BloodPart(rs.getInt("id" + tableName.toLowerCase()),
												rs.getInt("idblood"),
												rs.getDate("expirationdate"));

				bloodList.add(blood);
			}
		} catch (SQLException sqle) {
			return null;
		}
		return bloodList.stream();
	}
}
