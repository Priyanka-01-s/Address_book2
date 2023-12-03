import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Address {
    private String name;
    private Set<Contact> contacts;

    public Address(String name) {
        this.name = name;
        this.contacts = new HashSet<>();
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

                System.out.println("Contact updated successfully in " + name + " address book.\n");
                return;
            }
        }
        System.out.println("CONTACT NOT AVAILABLE in " + name);
    }

    public void deleteAddress(String firstName, String lastName) {
        contacts.removeIf(contact ->
                contact.getFname().equalsIgnoreCase(firstName) && contact.getLname().equalsIgnoreCase(lastName));
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
}
