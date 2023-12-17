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

    public Connection getConnectivityTest() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/ADDRESSBOOK_DB";
        String username = "root";
        String password = "riya123";
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Database connection successful!");
        return connection;
    }

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
}
