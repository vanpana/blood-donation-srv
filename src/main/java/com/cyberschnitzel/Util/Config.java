package com.cyberschnitzel.Util;

import com.cyberschnitzel.Domain.Exceptions.ConfigException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Semaphore;

class Config {
    private final static String PROPERTIES_FILENAME = "config.properties";
    private static File propertiesFile;
    private static Semaphore mutex = new Semaphore(1);

    private final static String PROPERTY_DB_URL = "db.url";
    private final static String PROPERTY_DB_PORT = "db.port";
    private final static String PROPERTY_DB_NAME = "db.name";
    private final static String PROPERTY_DB_USER = "db.user";
    private final static String PROPERTY_DB_PASSWORD = "db.password";
    private final static String PROPERTY_DB_CREATION = "db.creation";
    private final static String PROPERTY_DB_CREATION_FILENAME = "db.creation_sql";

    private final static String PROPERTY_TEST_DB_URL = "testdb.url";
    private final static String PROPERTY_TEST_DB_PORT = "testdb.port";
    private final static String PROPERTY_TEST_DB_NAME = "testdb.name";
    private final static String PROPERTY_TEST_DB_USER = "testdb.user";
    private final static String PROPERTY_TEST_DB_PASSWORD = "testdb.password";

    private final static String PROPERTY_SALT = "h.salt";
    private final static String PROPERTY_PEPPER = "h.pepper";

    /**
     * Get the default config properties file
     *
     * @return File representing the properties file
     * @throws ConfigException if resource can't be fetched
     */
    public static File getPropertiesFile() throws ConfigException {
        try {
            mutex.acquire();
            if (propertiesFile == null) propertiesFile = getResourcesFile(PROPERTIES_FILENAME);
        } catch (InterruptedException ie) {
            System.out.println("Failed to acquire Config mutex: " + ie.getMessage());
        } finally {
            mutex.release();
        }
        return propertiesFile;
    }

    /**
     * Get the properties file from the disk.
     *
     * @param filename - The filename of the .properties file
     * @return File representing the properties file
     * @throws ConfigException if resource can't be fetched
     */
    public static File getResourcesFile(String filename) throws ConfigException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filename);

        // Check if url fetched correctly
        if (url != null) {
            // Fix for Windows users who have a space in their name
            if (url.toString().contains("%20")) {
                // Replace with space
                String fixed = url.toString().replace("%20", " ");

                // Try to rebuild the URL
                try {
                    url = new URL(fixed);
                } catch (MalformedURLException e) {
                    throw new ConfigException("Failed to reconstruct URL: " + e.getMessage());
                }
            }
            return new File(url.getFile());
        } else throw new ConfigException("Couldn't get resource");
    }

    /**
     * Get value from properties file by key
     *
     * @param key - the key to be searched for
     * @return value - String representing the value from that key
     */
    private static String getValueFromPropertyKey(String key) {
        Properties properties = new Properties();
        InputStream input = null;
        String value = null;

        try {
            // Get file from resources folder
            input = new FileInputStream(getPropertiesFile());

            // Load the properties from the file
            properties.load(input);

            // Fetch the desired value
            value = properties.getProperty(key);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found:" + fnfe.getMessage());
        } catch (ConfigException ce) {
            System.out.println("Config exception:" + ce.getMessage());
        } catch (IOException ioe) {
            System.out.println("I/O Exception:" + ioe.getMessage());
        } finally {
            // Safely close the input
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Failed closing properties file: " + e.getMessage());
                }
            }
        }

        return value;
    }

    /**
     * Add a property key with a value
     * @param key - key
     * @param value - value
     * @return true if the operation succeeded, false otherwise
     */
    private static boolean writeValueToPropertyKey(String key, String value) {
        Properties properties = new Properties();
        InputStream input;
        FileOutputStream output = null;
        String comment = "Key: " + key + ", Value: " + value;
        boolean result = false;

        try {
            // Get file from resources folder
            input = new FileInputStream(getPropertiesFile());

            // Load the properties from the file
            properties.load(input);

            // Safely close the input
            input.close();

            // Set the value to the property key
            properties.setProperty(key, value);

            // Open the output stream
            output = new FileOutputStream(getPropertiesFile());

            // Store the properties file
            properties.store(output, comment);

            // Set the result of the operation
            result = true;
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found:" + fnfe.getMessage());
        } catch (ConfigException ce) {
            System.out.println("Config exception:" + ce.getMessage());
        } catch (IOException ioe) {
            System.out.println("I/O Exception:" + ioe.getMessage());
        } finally {
            // Safely close the output
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("Failed closing properties file: " + e.getMessage());
                }
            }
        }
        return result;
    }

    // DATABASE CONFIG
    public static String getDatabaseURL() {
        return getValueFromPropertyKey(PROPERTY_DB_URL);
    }

    public static String getDatabaseName() {
        return getValueFromPropertyKey(PROPERTY_DB_NAME);
    }

    public static int getDatabasePort() {
        return Integer.valueOf(getValueFromPropertyKey(PROPERTY_DB_PORT));
    }

    public static String getDatabaseUser() {
        return getValueFromPropertyKey(PROPERTY_DB_USER);
    }

    public static String getDatabasePassword() {
        return getValueFromPropertyKey(PROPERTY_DB_PASSWORD);
    }

    public static boolean getDatabaseCreationRequest() {
        boolean value = getValueFromPropertyKey(PROPERTY_DB_CREATION).equals("true");

        if (value) value = writeValueToPropertyKey(PROPERTY_DB_CREATION, "false");

        return value;
    }

    public static String getDatabaseCreationSqlFile() {
        return getValueFromPropertyKey(PROPERTY_DB_CREATION_FILENAME);
    }

    // TEST DATABASE CONFIG
    public static String getTestDatabaseURL() {
        return getValueFromPropertyKey(PROPERTY_TEST_DB_URL);
    }

    public static String getTestDatabaseName() {
        return getValueFromPropertyKey(PROPERTY_TEST_DB_NAME);
    }

    public static int getTestDatabasePort() {
        return Integer.valueOf(getValueFromPropertyKey(PROPERTY_TEST_DB_PORT));
    }

    public static String getTestDatabaseUser() {
        return getValueFromPropertyKey(PROPERTY_TEST_DB_USER);
    }

    public static String getTestDatabasePassword() {
        return getValueFromPropertyKey(PROPERTY_TEST_DB_PASSWORD);
    }

    // HASHER CONFIG
    public static String getHasherSalt() {
        return getValueFromPropertyKey(PROPERTY_SALT);
    }

    public static String getHasherPepper() {
        return getValueFromPropertyKey(PROPERTY_PEPPER);
    }
}
