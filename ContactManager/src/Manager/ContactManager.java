package src.Manager;

import java.util.ArrayList;
import java.util.regex.Pattern;
import src.Database.ContactDatabase;

//Manager to manage a list of contacts with necessary tool/methods
public class ContactManager {
    private static ArrayList<ContactStructure> contacts = new ArrayList<>();
    private final Pattern PHONE_NUM_PATTERN = Pattern.compile("[\\d\\s\\-*#+]+");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^.+@[\\w\\-]+\\.[\\w\\-]*\\w$");
    private ContactDatabase contactDatabase = new ContactDatabase();

    // Validate and add to contact and return a error message if any
    public String addContact(String name, String number, String email) {
        if (validatePhoneNum(number) && validatename(name)
                && (validateEmail(email) || email == null || email.isEmpty())) {
            contactDatabase.addContact(name, number, email);
            refreshContacts();
            return null;
        }
        if (!(validateEmail(email) || email == null || email.isEmpty()))
            return "Invalid Email! Remove if not available";
        return "Invalid Name or Number!";
    }

    // Searches the list for contacts with exact match and returns contact else null
    public ContactStructure searchContact(int id) {
        if (id <= 0)
            return null;
        return contactDatabase.searchContact(id);
    }

    // Searches if a contact contains the name in and returns the contact else null
    public ArrayList<ContactStructure> searchContact(String nameOrPhone) {
        if (nameOrPhone == null || nameOrPhone.isBlank())
            return null;
        return contactDatabase.searchContacts(nameOrPhone);
    }

    // takes contact as input instead of name and deletes it directly
    public ContactStructure deleteContact(ContactStructure contact) {
        if (contact != null && contactDatabase.deleteContact(contact.getId())) {
            refreshContacts();
            return contact;
        }
        return null;
    }

    public ArrayList<ContactStructure> getContacts() {
        refreshContacts();
        return contacts;
    }

    // takes in the contact reference and updates its details and return any error
    // message
    public String updateContact(ContactStructure contact, String name, String number, String email) {
        if (validatePhoneNum(number) && validatename(name)
                && (email == null || validateEmail(email) || email.isEmpty())) {
            contactDatabase.updateContact(contact.getId(), name, number, email);
            refreshContacts();
            return null;
        }
        if (!(email == null || validateEmail(email) || email.isEmpty()))
            return "Invalid Email! Remove if unavailable";
        return "Invalid Name or Number!";
    }

    // name validation checks if name is empty or has only spaces
    private boolean validatename(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // Basic number validation
    private boolean validatePhoneNum(String phone) {
        return phone != null && !phone.trim().isEmpty() && PHONE_NUM_PATTERN.matcher(phone).matches();
    }

    // Basic email validation
    private boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Remove contact list and fetch 10 contacts from database
    public void refreshContacts() {
        contacts.clear();
        contacts.addAll(contactDatabase.fetchContacts());
    }

    // Returns every contact in the list in single long String formatted
    @Override
    public String toString() {
        refreshContacts();
        String details = "";
        refreshContacts();
        if (contacts.isEmpty())
            return "Empty Contact List";
        for (ContactStructure contact : contacts)
            details += contact.toString() + "\n";
        return details + "\nEOF\n";
    }
}
