package codeSoft_numbergame;
import java.util.*;
import java.io.*;
import java.util.regex.*;

// 1.Create a Contact class to represent individual contacts. Include attributes such as name, phone number, email address, and any other relevant details.
class Contact implements Serializable {
    private String name;
    private String phone_Number;
    private String email_Address;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phone_Number = phoneNumber;
        this.email_Address = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone_Number;
    }

    public String getEmailAddress() {
        return email_Address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_Number = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.email_Address = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone number: " + phone_Number + ", Email id: " + email_Address;
    }
}

// 2.Implement an Address Book class to manage the collection of contacts. Include methods to add a contact, remove a contact, search for a contact, and display all contacts
class AddressBook {
    private List<Contact> contacts;
    private String storageFile;

    public AddressBook(String storageFile) {
        this.storageFile = storageFile;
        this.contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        if (validateContact(contact)) {
            contacts.add(contact);
            saveContacts();
        } else {
            System.out.println("Invalid contact data. Please try again.");
        }
    }

    public void removeContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        saveContacts();
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
            contacts = new ArrayList<>();
        }
    }

// 6.Implement input validation to ensure that required fields are not left empty and that the contact data is in the correct format.
    private boolean validateContact(Contact contact) {
        if (contact.getName().isEmpty() || contact.getPhoneNumber().isEmpty() || contact.getEmailAddress().isEmpty()) {
            return false;
        }
        if (!Pattern.matches("\\d{10}", contact.getPhoneNumber())) {
            return false;
        }
        if (!Pattern.matches("[^@]+@[^@]+\\.[^@]+", contact.getEmailAddress())) {
            return false;
        }
        return true;
    }
}

// 3.Design the user interface for the Address Book System. This can be a console-based interface.
public class AddressBookApp {
	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        AddressBook addressBook = new AddressBook("contacts.ser");

	        while (true) {
//5.Allow users to interact with the Address Book System by providing options such as adding a new contact, editing an existing contact's information, searching for a contact, displaying all contacts, and exiting the application.
	            System.out.println("\nAddress Book Menu:");
	            System.out.println("1. Add a new contact");
	            System.out.println("2. Edit an existing contact");
	            System.out.println("3. Search for a contact");
	            System.out.println("4. Display all contacts");
	            System.out.println("5. Remove a contact");
	            System.out.println("6. Exit");

	            System.out.print("Choose an option (1-6): ");
	            String choice = scanner.nextLine();

	            switch (choice) {
	                case "1":
	                    System.out.print("Enter name: ");
	                    String name = scanner.nextLine();
	                    System.out.print("Enter phone number: ");
	                    String phoneNumber = scanner.nextLine();
	                    System.out.print("Enter email address: ");
	                    String emailAddress = scanner.nextLine();
	                    Contact contact = new Contact(name, phoneNumber, emailAddress);
	                    addressBook.addContact(contact);
	                    System.out.println("contact added to address book");
	                    break;
//4.Implement methods to read and write contact data to a storage medium, such as a file or a database.
	                case "2":
	                    System.out.print("Enter the name of the contact to edit: ");
	                    String editName = scanner.nextLine();
	                    Contact editContact = addressBook.searchContact(editName);
	                    if (editContact != null) {
	                        System.out.print("Enter new name (current: " + editContact.getName() + "): ");
	                        String newName = scanner.nextLine();
	                        System.out.print("Enter new phone number (current: " + editContact.getPhoneNumber() + "): ");
	                        String newPhone = scanner.nextLine();
	                        System.out.print("Enter new email address (current: " + editContact.getEmailAddress() + "): ");
	                        String newEmail = scanner.nextLine();
	                        editContact.setName(newName.isEmpty() ? editContact.getName() : newName);
	                        editContact.setPhoneNumber(newPhone.isEmpty() ? editContact.getPhoneNumber() : newPhone);
	                        editContact.setEmailAddress(newEmail.isEmpty() ? editContact.getEmailAddress() : newEmail);
	                        addressBook.saveContacts();
	                        System.out.println("the contact is edited");
	                    } else {
	                        System.out.println("Contact not found.");
	                    }
	                    break;
	                case "3":
	                    System.out.print("Enter the name of the contact to search: ");
	                    String searchName = scanner.nextLine();
	                    Contact searchContact = addressBook.searchContact(searchName);
	                    if (searchContact != null) {
	                        System.out.println(searchContact);
	                    } else {
	                        System.out.println("Contact not found.");
	                    }
	                    break;
	                case "4":
	                    addressBook.displayAllContacts();
	                    break;
	                case "5":
	                    System.out.print("Enter the name of the contact to remove: ");
	                    String removeName = scanner.nextLine();
	                    addressBook.removeContact(removeName);
	                    System.out.println("the contact is removed");
	                    break;
	                case "6":
	                    System.out.println("Exiting Address Book. Good Day!");
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Invalid choice. Please choose a valid option.");
	            }
	        }
	    }
}

