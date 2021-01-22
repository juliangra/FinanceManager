package com.financemanager.controllers;

import com.financemanager.models.Person;

import java.io.IOException;
import java.sql.*;
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

        setStatement(person, statement);

        statement.executeUpdate();
    }

    public Person read(String query) throws SQLException {
        logger.info("Read from the database");

        // TODO: Implement support for specific queries
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            logger.warning("There is no data in the database.");
            return null;
        }

        var person = new Person();
        person.setId(resultSet.getLong("PersonId"));
        person.setFirstName(resultSet.getString("FirstName"));
        person.setLastName(resultSet.getString("LastName"));
        person.setEmail(resultSet.getString("Email"));
        person.setPassword(resultSet.getString("Password"));
        person.setBalance(resultSet.getDouble("Balance"));
        person.setAccountNumber(resultSet.getInt("AccountNumber"));

        logger.info("Data read from database: " + person);

        return person;
    }

    public void update(Person person) throws SQLException {
        logger.info("Updating the database");

        PreparedStatement statement = connection.prepareStatement("UPDATE Person SET FirstName = ?, LastName = ?, Email = ?, Password = ?, Balance = ?, AccountNumber = ? WHERE PersonId = ?");

        setStatement(person, statement);
        statement.setLong(7, person.getId());

        statement.executeUpdate();
    }

    public void delete(Person person) throws SQLException {
        logger.info("Deleting from the database");

        PreparedStatement statement = connection.prepareStatement("DELETE FROM Person WHERE PersonId = ?");

        statement.setLong(1, person.getId());

        statement.executeUpdate();
    }

    private void setStatement(Person person, PreparedStatement statement) throws SQLException {
        statement.setString(1, person.getFirstName());
        statement.setString(2, person.getLastName());
        statement.setString(3, person.getEmail());
        statement.setString(4, person.getPassword());
        statement.setDouble(5, person.getBalance());
        statement.setInt(6, person.getAccountNumber());
    }
}
