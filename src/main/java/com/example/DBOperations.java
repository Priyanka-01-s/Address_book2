package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// public class DBOperations {

//     public Connection getConnectivityTest() throws SQLException {
//         String jdbcUrl = "jdbc:mysql://localhost:3306/ADDRESSBOOK_DB";
//         String username = "root";
//         String password = "riya123";
//         try (
//                 Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//             System.out.println("Database connection successful!");
//         } catch (Exception e) {
//             System.out.println("Error : Database connection failed!");
//             e.printStackTrace();
//         }
//         return DriverManager.getConnection(jdbcUrl, username, password);
//     }

//     public List<Contact> getPayrollData() throws SQLException ContactRetrivalException{
//         List<Contact> contacts = new ArrayList<>();

//         try (Connection connection = getConnectivityTest()) {
//             String query = "SELECT * FROM addressbook_table2";
//             try (Statement statement = connection.createStatement();
//                     ResultSet resSet = statement.executeQuery(query)) {
//                         while (resSet.next()) {
//                                         // Process or print the retrieved values as needed

                                       
//                     String fname = resSet.getString("FIRSTNAME");
//                     String lname = resSet.getString("LASTNAME");
//                     String address = resSet.getString("ADDRESS");
//                     String city = resSet.getString("CITY");
//                     String state = resSet.getString("STATE");
//                     String zip = resSet.getString("ZIP");
//                     String phone = resSet.getString("PHONE");
//                     String email = resSet.getString("EMAIL");
//                     // String fullName = resSet.getString("FULL_NAME");
//                     // String type = resSet.getString("ENTRY_TYPE");
                                        
                                    

//                     // create an EmployeePayroll object and add it to the list
//                     Contact contact = new Contact(fname, lname, address, city, state, zip, phone,email);
//                     contacts.add(contact);

//                 }
            

//             } catch (SQLException e) {
//                 throw new ContactRetrivalException("Error retrieving address book data: " + e.getMessage());
//             }
//             return contacts;
//         }

//     }
// }

public class DBOperations {

    public Connection getConnectivityTest() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/ADDRESSBOOK_DB";
        String username = "root";
        String password = "riya123";

        // Remove try-with-resources around Connection
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Database connection successful!");
        return connection;
    }

    public List<Contact> getPayrollData() throws ContactRetrivalException {
        List<Contact> contacts = new ArrayList<>();

        try (Connection connection = getConnectivityTest()) {
            // Modify SQL query as needed
            String query = "SELECT * FROM addressbook_table2";
            try (Statement statement = connection.createStatement();
                 ResultSet resSet = statement.executeQuery(query)) {
                while (resSet.next()) {
                    // Process or print the retrieved values as needed
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
