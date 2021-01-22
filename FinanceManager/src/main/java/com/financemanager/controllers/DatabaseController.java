package com.financemanager.controllers;

import com.financemanager.models.Person;

import java.io.IOException;
import java.sql.SQLException;

public interface DatabaseController {
    /**
     * Loads application properties.
     */
    void loadApplicationProperties() throws IOException;


    /**
     * @throws SQLException if database service fails.
     *                      Connects to the implemented database service.
     */
    void connectToDatabase() throws SQLException;


    /**
     * @throws SQLException if database service fails.
     *                      Closes connection with implemented database service.
     */
    void closeDatabaseConnection() throws SQLException;


    /**
     * @param person describes an instance of the Person class.
     *               Inserts a person into the database.
     * @throws SQLException if database service fails.
     */
    void insert(Person person) throws SQLException;

    /**
     * @param query represents the data requested from the database.
     * @return an instance of the Person class.
     * @throws SQLException if database service fails.
     */
    Person read(String query) throws SQLException;


    /**
     * @param person represents an instance of the Person class.
     * @throws SQLException if database service fails.
     */
    void update(Person person) throws SQLException;

    void delete(Person person) throws SQLException;
}
