package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Main {

    public static void main(String[] args) throws CsvValidationException {
        System.out.println("-------WELCOME TO ADDRESS BOOK PROGRAM-------");

        AddressManager addressBookManager = new AddressManager();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            printMainMenu();
            choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    createAddressBook(addressBookManager, scanner);
                    break;

                case 2:
                    operateOnExistingAddressBook(addressBookManager, scanner);
                    break;

                case 3:
                    searchByCity(addressBookManager, scanner);
                    break;

                case 0:
                    System.out.println("---------THANK YOU FOR USING ADDRESS BOOK---------");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 0);
    }

    private static void printMainMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Create a new address book");
        System.out.println("2. Operate on an existing address book");
        System.out.println("3. Search for a person by city");
        System.out.println("0. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static void createAddressBook(AddressManager addressBookManager, Scanner scanner) {
        System.out.print("Enter the name of the new address book: ");
        String newBookName = scanner.next();
        addressBookManager.createAddressBook(newBookName);
    }

    private static void operateOnExistingAddressBook(AddressManager addressBookManager, Scanner scanner) throws CsvValidationException {
        System.out.print("Enter the name of the address book to operate on: ");
        String addressBookName = scanner.next();

        Address selectedAddressBook = addressBookManager.getAddress(addressBookName);

        if (selectedAddressBook != null) {
            operateOnAddressBook(selectedAddressBook, scanner);
        } else {
            System.out.println("Address book '" + addressBookName + "' does not exist.");
        }
    }

    private static void operateOnAddressBook(Address selectedAddressBook, Scanner scanner) throws CsvValidationException {
        int choice;
        do {
            printAddressBookMenu(selectedAddressBook.getName());
            choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addContact(selectedAddressBook, scanner);
                    break;

                case 2:
                    editContact(selectedAddressBook, scanner);
                    break;

                case 3:
                    deleteContact(selectedAddressBook, scanner);
                    break;

                case 4:
                    selectedAddressBook.display();
                    break;

                case 5:
                    viewPersonsByCity(selectedAddressBook, scanner);
                    break;

                case 6:
                    viewPersonsByState(selectedAddressBook, scanner);
                    break;

                case 7:
                    displaySortedContacts(selectedAddressBook);
                    break;

                case 8:
                    storeContactsInCSV(selectedAddressBook, scanner);
                    break;

                case 9:
                    readContactsFromCSV(selectedAddressBook, scanner);
                    break;

                case 0:
                    System.out.println("Exiting to the main menu.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private static void printAddressBookMenu(String addressBookName) {
        System.out.println("---------ADDRESS BOOK OPERATIONS - " + addressBookName + "---------");
        System.out.println("1. Add a new contact");
        System.out.println("2. Edit an existing contact");
        System.out.println("3. Delete a contact");
        System.out.println("4. Display all contacts");
        System.out.println("5. View persons by city");
        System.out.println("6. View persons by state");
        System.out.println("7. Display sorted contacts");
        System.out.println("8. Store contacts in CSV file");
        System.out.println("9. Read data from the csv file");
        System.out.println("0. Exit");
    }

    private static void addContact(Address selectedAddressBook, Scanner scanner) {
        try {
            System.out.print("Enter first name: ");
            String fname = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lname = scanner.nextLine();

            System.out.print("Enter address: ");
            String addressStr = scanner.nextLine();

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

            Contact newContact = new Contact(fname, lname, addressStr, city, state, zip, phone, email);
            selectedAddressBook.addAddress(newContact);

            saveContactToFile(newContact);

            System.out.println("Contact added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please enter valid details as per the regex rules.");
        }
    }

    private static void editContact(Address selectedAddressBook, Scanner scanner) {
        System.out.println("Editing an existing contact:");

        System.out.print("Enter first name of the contact to edit: ");
        String editFirstName = scanner.nextLine();

        System.out.print("Enter last name of the contact to edit: ");
        String editLastName = scanner.nextLine();

        selectedAddressBook.editAddress(editFirstName, editLastName, scanner);
    }

    private static void deleteContact(Address selectedAddressBook, Scanner scanner) {
        System.out.println("Deleting an existing contact:");

        System.out.print("Enter first name of the contact to delete: ");
        String delFName = scanner.nextLine();

        System.out.print("Enter last name of the contact to delete: ");
        String delLName = scanner.nextLine();

        selectedAddressBook.deleteAddress(delFName, delLName);
    }

    private static void searchByCity(AddressManager addressBookManager, Scanner scanner) {
        System.out.print("Enter the city to search for: ");
        String cityToSearch = scanner.nextLine();

        List<Contact> searchResults = addressBookManager.searchByCityOrState(cityToSearch);

        if (!searchResults.isEmpty()) {
            System.out.println("Search Results in " + cityToSearch + ":");
            for (Contact contact : searchResults) {
                System.out.println(contact);
                System.out.println();
            }
        } else {
            System.out.println("No contacts found in the specified city.");
        }
    }

    private static void viewPersonsByCity(Address selectedAddressBook, Scanner scanner) {
        System.out.print("Enter the city to view persons: ");
        String cityToView = scanner.nextLine();

        List<Contact> contactsInCity = selectedAddressBook.getContactsByCity(cityToView);

        int count = selectedAddressBook.getCountByCity(cityToView);

        System.out.println("Count of persons in " + cityToView + ": " + count);
        if (!contactsInCity.isEmpty()) {
            System.out.println("Persons in " + cityToView + ":");
            for (Contact contact : contactsInCity) {
                System.out.println(contact);
                System.out.println();
            }
        } else {
            System.out.println("No persons found in the specified city.");
        }
    }

    private static void viewPersonsByState(Address selectedAddressBook, Scanner scanner) {
        System.out.print("Enter the state to view persons: ");
        String stateToView = scanner.nextLine();

        List<Contact> contactsInState = selectedAddressBook.getContactsByState(stateToView);

        int count = selectedAddressBook.getCountByState(stateToView);

        System.out.println("Count of persons in " + stateToView + ": " + count);

        if (!contactsInState.isEmpty()) {
            System.out.println("\nPersons in " + stateToView + ":");
            for (Contact contact : contactsInState) {
                System.out.println(contact);
                System.out.println();
            }
        } else {
            System.out.println("No persons found in the specified state.");
        }
    }

    private static void displaySortedContacts(Address selectedAddressBook) {
        selectedAddressBook.displaySorted();
    }

    private static void saveContactToFile(Contact contact) {
        try (FileWriter writer = new FileWriter("address.txt", true)) {
            // Append the contact information to the file
            writer.write("Name: " + contact.getFname() + " " + contact.getLname() + "\n");
            writer.write("Address: " + contact.getAddress() + ", " + contact.getCity() + ", " +
                    contact.getState() + " - " + contact.getZip() + "\n");
            writer.write("Phone: " + contact.getPhone() + ", Email: " + contact.getEmail() + "\n\n");
        } catch (IOException e) {
            System.out.println("Error saving contact to file: " + e.getMessage());
        }
    }

    private static void storeContactsInCSV(Address selectedAddressBook, Scanner scanner) {
        System.out.print("Enter the CSV file path to store contacts: ");
        String filePath = scanner.nextLine();
        selectedAddressBook.writeContactsToCSV(filePath);
    }

    private static void readContactsFromCSV(Address selectedAddressBook, Scanner scanner) throws CsvValidationException {
        System.out.print("Enter the CSV file path to read contacts from: ");
        String filePath = scanner.nextLine();
        selectedAddressBook.readContactsFromCSV(filePath);
    }

}
