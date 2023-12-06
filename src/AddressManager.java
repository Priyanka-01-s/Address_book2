import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddressManager implements Serializable{
    private Map<String, Address> addressBookManager;

    public AddressManager() {
        this.addressBookManager = new HashMap<>();
    }

    public void createAddressBook(String name) {
        if (!addressBookManager.containsKey(name)) {
            addressBookManager.put(name, new Address(name));
            System.out.println("Address book " + name + " created successfully.");
        } else {
            System.out.println("Address with " + name + " already exists.");
        }
    }

    public void displayAllAddressBooks() {
        if (addressBookManager.isEmpty()) {
            System.out.println("No address books available.");
        } else {
            System.out.println("List of Address Books:");
            addressBookManager.keySet().forEach(System.out::println);
        }
    }


     public List<Contact> searchByCityOrState(String cityOrState) {
        List<Contact> searchResults = new ArrayList<>();

        for (Address addressBook : addressBookManager.values()) {
            List<Contact> contacts = addressBook.searchByCityOrState(cityOrState);
            searchResults.addAll(contacts);
        }

        return searchResults;
    }

    public Address getAddress(String name) {
        return addressBookManager.get(name);
    }
    
    // public static AddressManager parseFromString(String data) {
    //     AddressManager addressManager = new AddressManager();
    
    //     // Assuming your text format is one line per contact
    //     String[] lines = data.split("\n");
    
    //     for (String line : lines) {
    //         // Assuming your Contact class has a method to create an instance from a string
    //         Contact contact = Contact.parseFromString(line);
    //         addressManager.addContact(contact);
    //     }
    
    //     return addressManager;
    //}
    

    
}

