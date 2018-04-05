package com.cyberschnitzel.Domain.Adapters;

import com.cyberschnitzel.Util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * Class which adapts every entity to a JDBC Repository
 *
 * @param <ID>: The identity data type
 * @param <T>:  The entity data type
 */
public interface Adapter<T> {
    Connection connection = DatabaseUtil.getConnection();

    /**
     * @param entity - The entity to be saved
     * @return a PreparedStatement to be executed in the Repository
     * @throws SQLException - if the PreparedStatement can't be created
     */
    PreparedStatement saveQuery(T entity) throws SQLException;

    /**
     * @param id - id of the entity to be deleted
     * @return a PreparedStatement to be executed in the Repository
     * @throws SQLException - if the PreparedStatement can't be created
     */
    PreparedStatement deleteQuery(Integer id) throws SQLException;

    /**
     * @param entity - The entity to be updated
     * @return a PreparedStatement to be executed in the Repository
     * @throws SQLException - if the PreparedStatement can't be created
     */
    PreparedStatement updateQuery(T entity) throws SQLException;

    /**
     * @param id - The id of the entity to be queried
     * @return a PreparedStatement to be executed in the Repository
     * @throws SQLException - if the PreparedStatement can't be created
     */
    PreparedStatement findOneQuery(Integer id) throws SQLException;

    /**
     * @return a PreparedStatement to be executed in the Repository
     * @throws SQLException - if the PreparedStatement can't be created
     */
    PreparedStatement findAllQuery() throws SQLException;

    Stream<T> get(ResultSet rs);
}