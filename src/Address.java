import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Address {
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

                String lowerCaseCity = contact.getCity().toLowerCase();
                String lowerCaseState = contact.getState().toLowerCase();

                cityDictionary.computeIfAbsent(lowerCaseCity, k -> new ArrayList<>()).add(contact);
                stateDictionary.computeIfAbsent(lowerCaseState, k -> new ArrayList<>()).add(contact);

                cityCount.put(lowerCaseCity, cityCount.getOrDefault(lowerCaseCity, 0) + 1);
                stateCount.put(lowerCaseState, stateCount.getOrDefault(lowerCaseState, 0) + 1);

                System.out.println("Contact added successfully to " + name);

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

    public int getCountByCity(String city) {
        return cityCount.getOrDefault(city, 0);
    }

    public List<Contact> getContactsByState(String state) {
        return stateDictionary.getOrDefault(state, new ArrayList<>());
    }
    
    public int getCountByState(String state) {
        return stateCount.getOrDefault(state, 0);
    }

    public void sortByCity() {
        List<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparing(Contact::getCity));
        displaySorted("Sorted by City", sortedList);
    }

    public void sortByState() {
        List<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparing(Contact::getState));
        displaySorted("Sorted by State", sortedList);
    }

    public void sortByZip() {
        List<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparing(Contact::getZip));
        displaySorted("Sorted by Zip", sortedList);
    }
    private void displaySorted(String message, List<Contact> sortedList) {
        System.out.println(message + " - " + name + " Address Book:\n");
        for (Contact contact : sortedList) {
            System.out.println(contact);
            System.out.println();
        }
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
}
