import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

                case 0:
                    System.out.println("---------THANK YOU FOR USING ADDRESS BOOK---------");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Create a new address book");
        System.out.println("2. Operate on an existing address book");
        System.out.println("0. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private static void createAddressBook(AddressManager addressBookManager, Scanner scanner) {
        System.out.print("Enter the name of the new address book: ");
        String newBookName = scanner.next();
        addressBookManager.createAddressBook(newBookName);
    }

    private static void operateOnExistingAddressBook(AddressManager addressBookManager, Scanner scanner) {
        System.out.print("Enter the name of the address book to operate on: ");
        String addressBookName = scanner.next();

        Address selectedAddressBook = addressBookManager.getAddress(addressBookName);

        if (selectedAddressBook != null) {
            operateOnAddressBook(selectedAddressBook, scanner);
        } else {
            System.out.println("Address book '" + addressBookName + "' does not exist.");
        }
    }

    private static void operateOnAddressBook(Address selectedAddressBook, Scanner scanner) {
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
        System.out.println("0. Exit");
    }

    private static void addContact(Address selectedAddressBook, Scanner scanner) {
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
}
