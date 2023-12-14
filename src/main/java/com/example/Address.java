package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class Address implements Serializable {
    private String name;
    private Set<Contact> contacts;
    private Map<String, List<Contact>> cityDictionary;
    private Map<String, List<Contact>> stateDictionary;
    private Map<String, Integer> cityCount;
    private Map<String, Integer> stateCount;

    public Address(String name) {
        this.name = name;
        this.contacts = new HashSet<>();
        this.cityDictionary = new HashMap<>();
        this.stateDictionary = new HashMap<>();
        this.cityCount = new HashMap<>();
        this.stateCount = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addAddress(Contact contact) {
        try {
            if (isDuplicate(contact)) {
                System.out.println("Duplicate entry! Contact with the same name already exists in " + name);
                return;
            }

            contacts.add(contact);
            cityDictionary.computeIfAbsent(contact.getCity(), k -> new ArrayList<>()).add(contact);

            stateDictionary.computeIfAbsent(contact.getState(), k -> new ArrayList<>()).add(contact);

            cityCount.put(contact.getCity(), cityCount.getOrDefault(contact.getCity(), 0) + 1);

            stateCount.put(contact.getState(), stateCount.getOrDefault(contact.getState(), 0) + 1);

            System.out.println("Contact added successfully to " + name);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please enter valid details as per the regex rules.");
        }
    }

    private boolean isDuplicate(Contact contact) {
        return contacts.contains(contact);
    }

    public void display() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts in " + name + " the address book");
        } else {
            System.out.println(name + " Address book updated\n");
            for (Contact c : contacts) {
                System.out.println(c);
                System.out.println();
            }
        }
    }

    public void editAddress(String firstName, String lastName, Scanner scanner) {
        for (Contact contact : contacts) {
            if (contact.getFname().equalsIgnoreCase(firstName) && contact.getLname().equalsIgnoreCase(lastName)) {
                System.out.println("Enter the updated details :");

                System.out.print("Enter address: ");
                String address = scanner.nextLine();

                System.out.print("Enter city: ");
                String city = scanner.nextLine();

                System.out.print("Enter state: ");
                String state = scanner.nextLine();

                System.out.print("Enter zip: ");
                String zip = scanner.nextLine();

                System.out.print("Enter phone number: ");
                String phone = scanner.nextLine();

                System.out.print("Enter email: ");
                String email = scanner.nextLine();

                Contact updatedContact = new Contact(firstName, lastName, address, city, state, zip, phone, email);
                contacts.remove(contact);
                contacts.add(updatedContact);

                cityDictionary.get(contact.getCity()).remove(contact);
                cityDictionary.computeIfAbsent(contact.getCity(), k -> new ArrayList<>()).add(contact);

                stateDictionary.get(contact.getState()).remove(contact);
                stateDictionary.computeIfAbsent(contact.getState(), k -> new ArrayList<>()).add(contact);

                System.out.println("Contact updated successfully in " + name + " address book.\n");
                return;
            }
        }
        System.out.println("CONTACT NOT AVAILABLE in " + name);
    }

    public void deleteAddress(String firstName, String lastName) {
        contacts.removeIf(contact -> contact.getFname().equalsIgnoreCase(firstName)
                && contact.getLname().equalsIgnoreCase(lastName));
        System.out.println("Contact deleted successfully from " + name);

    }

    public List<Contact> searchByCityOrState(String cityOrState) {
        List<Contact> searchResults = new ArrayList<>();

        for (Contact contact : contacts) {
            if (contact.getCity().equalsIgnoreCase(cityOrState) || contact.getState().equalsIgnoreCase(cityOrState)) {
                searchResults.add(contact);
            }
        }

        return searchResults;
    }

    public List<Contact> getContactsByCity(String city) {
        return cityDictionary.getOrDefault(city, new ArrayList<>());
    }

    public List<Contact> getContactsByState(String state) {
        return stateDictionary.getOrDefault(state, new ArrayList<>());
    }

    public int getCountByCity(String city) {
        return cityCount.getOrDefault(city, 0);
    }

    public int getCountByState(String state) {
        return stateCount.getOrDefault(state, 0);
    }

    public void displaySorted() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts in " + name + " the address book");
        } else {
            System.out.println(name + " Address book updated and sorted alphabetically by name:\n");

            List<Contact> sortedContacts = new ArrayList<>(contacts);
            Collections.sort(sortedContacts);

            for (Contact c : sortedContacts) {
                System.out.println(c);
                System.out.println();
            }
        }
    }

    public void writeContactsToCSV(String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                String[] contactData = {
                        contact.getFname(), contact.getLname(), contact.getAddress(),
                        contact.getCity(), contact.getState(), contact.getZip(),
                        contact.getPhone(), contact.getEmail()
                };
                writer.writeNext(contactData);
            }
            System.out.println("Contacts written to CSV file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing contacts to CSV file: " + e.getMessage());
        }
    }

    public void readContactsFromCSV(String filePath) throws CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    Contact newContact = new Contact(line[0], line[1], line[2], line[3], line[4], line[5], line[6],
                            line[7]);
                    addAddress(newContact);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error creating contact: " + e.getMessage());
                }
            }
            System.out.println("Contacts loaded successfully from the CSV file.");
        } catch (IOException e) {
            System.out.println("Error reading contacts from CSV file: " + e.getMessage());
        }
    }

}
