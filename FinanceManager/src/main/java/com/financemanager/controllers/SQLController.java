package com.financemanager.controllers;

import com.financemanager.models.Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class SQLController implements DatabaseController {
    private static Logger logger;
    private Properties properties;
    private Connection connection;

    public SQLController() {
        // Initialize logging
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        logger = Logger.getLogger(SQLController.class.getName());
    }

    public void loadApplicationProperties() throws IOException {
        logger.info("Loading application properties");
        properties = new Properties();
        properties.load(SQLController.class.getClassLoader().getResourceAsStream("application.properties"));
    }

    public void connectToDatabase() throws SQLException {
        logger.info("Connecting to the database");
        connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        logger.info("Database connection test: " + connection.getCatalog());
    }

    public void closeDatabaseConnection() throws SQLException {
        logger.info("Closing database connection");
        connection.close();
    }

    public void insert(Person person) throws SQLException {
        logger.info("Inserting to the database");

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Person (FirstName, LastName, Email, Password, Balance, AccountNumber) VALUES (?, ?, ?, ?, ?, ?);");

        statement.setString(1, person.getFirstName());
        statement.setString(2, person.getLastName());
        statement.setString(3, person.getEmail());
        statement.setString(4, person.getPassword());
        statement.setDouble(5, person.getBalance());
        statement.setInt(6, person.getAccountNumber());

        statement.executeUpdate();
    }
}
