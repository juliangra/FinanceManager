package com.financemanager.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class SQLController {
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
}
