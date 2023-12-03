import java.util.HashMap;
import java.util.Map;

class AddressManager {
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

    public Address getAddress(String name) {
        return addressBookManager.get(name);
    }
}

