package com.cyberschnitzel.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

public class DatabaseUtil {
    private static Connection connection;

    private static String DATABASE_URL;
    private static int PORT;
    private static String DATABASE_NAME;
    private static String USER;
    private static String PASSWORD;

    private static final Semaphore mutex = new Semaphore(1);

    /**
     * Method which connects to the database.
     *
     * @return connection
     */
    public static Connection getConnection() {
        try {
            // Acquire the mutex
            mutex.acquire();

            // Load the credentials from the property file
            fetchCredentials();

            // If the connection was already made and it's not closed, release the mutex and return the connection
            if (connection != null && !connection.isClosed()) {
                mutex.release();
                return connection;
            }

            // Load the JDBC class
            Class.forName("org.postgresql.Driver");

            // Get the connection
            connection = DriverManager.getConnection("jdbc:postgresql://" + DATABASE_URL + ":" + PORT + "/" +
                    DATABASE_NAME, USER, PASSWORD);

        } catch (InterruptedException ie) {
            System.out.println("Failed to acquire mutex: " + ie.getMessage());
        } catch (SQLException sqle) {
            System.out.println("SQL Exception thrown: " + sqle.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to find class: " + e.getMessage());
        } finally {
            // Safely release the mutex
            mutex.release();
        }

        return connection;
    }

    /**
     * Safely close the database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("Failed to close database connection: " + sqle.getMessage());
        }
    }

    /**
     * Loads the values for the credentials from the configuration file
     */
    private static void fetchCredentials() {
        if (!checkCredentials()) {
            DATABASE_URL = Config.getDatabaseURL();
            PORT = Config.getDatabasePort();
            DATABASE_NAME = Config.getDatabaseName();
            USER = Config.getDatabaseUser();
            PASSWORD = Config.getDatabasePassword();
        }
    }

    /**
     * Checks if the credentials were instantiated
     * @return true if the credentials were instantiated, false otherwise
     */
    private static boolean checkCredentials() {
        return DATABASE_URL != null && PORT > 0 && DATABASE_NAME != null && USER != null && PASSWORD != null;
    }
}
