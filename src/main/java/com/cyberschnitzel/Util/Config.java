package com.cyberschnitzel.Util;

import com.cyberschnitzel.Domain.Exceptions.ConfigException;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Semaphore;

public class Config {
    private final static String PROPERTIES_FILENAME = "config.properties";
    private static File propertiesFile;
    private static Semaphore mutex = new Semaphore(1);

    private final static String PROPERTY_DB_URL = "db.url";
    private final static String PROPERTY_DB_PORT = "db.port";
    private final static String PROPERTY_DB_NAME = "db.name";
    private final static String PROPERTY_DB_USER = "db.user";
    private final static String PROPERTY_DB_PASSWORD = "db.password";

    /**
     * Get the default config properties file
     *
     * @return File representing the properties file
     * @throws ConfigException if resource can't be fetched
     */
    private static File getPropertiesFile() throws ConfigException {
        try {
            mutex.acquire();
            if (propertiesFile == null) propertiesFile = getPropertiesFile(PROPERTIES_FILENAME);
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
    private static File getPropertiesFile(String filename) throws ConfigException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filename);

        if (url != null) return new File(url.getFile());
        else throw new ConfigException("Couldn't get resource");
    }

    /**
     * Get value from properties file by key
     *
     * @param key - the key to be searched for
     * @return value - String representing the value from that key
     */
    private static String getValueFromPropertiesKey(String key) {
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

    // DATABASE CONFIG
    public static String getDatabaseURL() {
        return getValueFromPropertiesKey(PROPERTY_DB_URL);
    }

    public static String getDatabaseName() {
        return getValueFromPropertiesKey(PROPERTY_DB_NAME);
    }

    public static int getDatabasePort() {
        return Integer.valueOf(getValueFromPropertiesKey(PROPERTY_DB_PORT));
    }

    public static String getDatabaseUser() {
        return getValueFromPropertiesKey(PROPERTY_DB_USER);
    }

    public static String getDatabasePassword() {
        return getValueFromPropertiesKey(PROPERTY_DB_PASSWORD);
    }
}
