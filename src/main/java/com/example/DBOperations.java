package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {

    //getting the database connection
    public Connection getConnectivityTest() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/ADDRESSBOOK_DB";
        String username = "root";
        String password = "riya123";
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Database connection successful!");
        return connection;
    }

    //Retrival of contacts from address book database 
    public List<Contact> getAddressData() throws ContactRetrivalException {
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = getConnectivityTest()) {
            String query = "SELECT * FROM addressbook_table2";
            try (Statement statement = connection.createStatement();
                 ResultSet resSet = statement.executeQuery(query)) {
                while (resSet.next()) {
                    String fname = resSet.getString("FIRSTNAME");
                    String lname = resSet.getString("LASTNAME");
                    String address = resSet.getString("ADDRESS");
                    String city = resSet.getString("CITY");
                    String state = resSet.getString("STATE");
                    String zip = resSet.getString("ZIP");
                    String phone = resSet.getString("PHONE");
                    String email = resSet.getString("EMAIL");

                    // create a Contact object and add it to the list
                    Contact contact = new Contact(fname, lname, address, city, state, zip, phone, email);
                    contacts.add(contact);
                }
            } catch (SQLException e) {
                throw new ContactRetrivalException("Error retrieving address book data: " + e.getMessage());
            }
            return contacts;
        } catch (SQLException e) {
            throw new ContactRetrivalException("Error establishing database connection: " + e.getMessage());
        }
    }

    //updating the values in the database
    public void updateContactInfo(String firstName, String lastName, String newAddress, String newCity, String newState,
                                  String newZip, String newPhone, String newEmail) throws SQLException {
        String updateQuery = "UPDATE ADDRESSBOOK_TABLE2 SET ADDRESS=?, CITY=?, STATE=?, ZIP=?, PHONE=?, EMAIL=? " +
                "WHERE FIRSTNAME=? AND LASTNAME=?";
        
        try (Connection connection = getConnectivityTest();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newAddress);
            preparedStatement.setString(2, newCity);
            preparedStatement.setString(3, newState);
            preparedStatement.setString(4, newZip);
            preparedStatement.setString(5, newPhone);
            preparedStatement.setString(6, newEmail);
            preparedStatement.setString(7, firstName);
            preparedStatement.setString(8, lastName);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Contact information updated successfully for " + firstName + " " + lastName);
            } else {
                System.out.println("Contact not found for " + firstName + " " + lastName);
            }
        }
    }

    public List<Contact> getContactsByCity(String city, String state) throws ContactRetrivalException{
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = getConnectivityTest()) {
            String query = "SELECT * FROM addressbook_table2 WHERE CITY=? OR STATE=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, city);
                preparedStatement.setString(2, state);
                

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String fname = resultSet.getString("FIRSTNAME");
                        String lname = resultSet.getString("LASTNAME");
                        String address = resultSet.getString("ADDRESS");
                        String zip = resultSet.getString("ZIP");
                        String phone = resultSet.getString("PHONE");
                        String email = resultSet.getString("EMAIL");

                        Contact contact = new Contact(fname, lname, address, city, state, zip, phone, email);
                        contacts.add(contact);
                    }
                }
            } catch (SQLException e) {
                throw new ContactRetrivalException("Error retrieving contacts by city: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new ContactRetrivalException("Error establishing database connection: " + e.getMessage());
        }

        return contacts;
    }
}
