import java.util.ArrayList;
import java.util.Scanner;

class Address {

    private String name;
    private ArrayList<Contact> contacts;

    public Address(String name) {
        this.name = name;
        this.contacts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAddress(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact added successfully to " + name);
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

                contact.updateContact(address, city, state, zip, phone, email);
                System.out.println("Contact updated successfully in " + name + " address book.\n");
                return;
            }else{
                System.out.println("CONTACT NOT AVAILABLE in " + name);
            }
        }
        
    }

    public void deleteAddress(String firstName, String lastName) {
        for (Contact contact : contacts) {
            if (contact.getFname().equalsIgnoreCase(firstName) && contact.getLname().equalsIgnoreCase(lastName)) {
                contacts.remove(contact);
                System.out.println("Contact deleted successfully from " + name);
                return;
            } else {
                System.out.println("\nCONTACT NOT AVAILABLE IN " + name);
            }
        }
    }
}