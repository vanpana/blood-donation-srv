package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Domain.Entities.BloodPart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BloodPartAdapter implements Adapter<BloodPart> {

	private String tableName;


	public BloodPartAdapter(String partName )
	{
		tableName = partName;
	}
	@Override
	public PreparedStatement saveQuery(BloodPart entity) throws SQLException {
		BloodPart b = (BloodPart)entity;
		String query = "INSERT INTO " + "\"" + tableName + "\"" + "(id" + tableName.toLowerCase() + ", idblood, expirationdate) " +
				"VALUES (?, ?, ?)";
		PreparedStatement p = connection.prepareStatement(query);
		p.setInt(1,b.getId());
		p.setInt(2,b.getIdBlood());
		p.setDate(3,new java.sql.Date(b.getExpirationDate().getTime()));
		return p;
	}

	@Override
	public PreparedStatement deleteQuery(Integer id) throws SQLException {
		String query = "DELETE FROM " + "\"" + tableName + "\"" + " WHERE id" + tableName.toLowerCase() + " = ?";
		PreparedStatement p = connection.prepareStatement(query);
		p.setInt(1,id);
		return p;
	}

	@Override
	public PreparedStatement updateQuery(BloodPart entity) throws SQLException {
		BloodPart b = (BloodPart)entity;
		String query = "UPDATE " + "\"" + tableName + "\"" + " SET id" + tableName.toLowerCase() + " = ?, idblood = ?, expirationdate = ? " +
				"WHERE id" + tableName.toLowerCase() + " = ?";
		PreparedStatement p = connection.prepareStatement(query);
		p.setInt(1,b.getId());
		p.setInt(2,b.getIdBlood());
		p.setDate(3,new java.sql.Date(b.getExpirationDate().getTime()));
		p.setInt(4,b.getId());
		return p;
	}

	@Override
	public PreparedStatement findOneQuery(Integer id) throws SQLException {
		String query = "SELECT * FROM " + "\"" + tableName + "\"" + " WHERE id" + tableName.toLowerCase() + " = ?";
		PreparedStatement p = connection.prepareStatement(query);
		p.setInt(1,id);
		return p;
	}


	@Override
	public PreparedStatement findAllQuery() throws SQLException {
		String query = "SELECT * FROM " + "\"" + tableName + "\"";
		return connection.prepareStatement(query);
	}

	@Override
	public Stream<BloodPart> get(ResultSet rs) {
		List<BloodPart> bloodList = new ArrayList<>();
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
