package src.Manager;

import java.util.ArrayList;

//Manager to manage a list of contacts
public class ContactManager {
    private static ArrayList<ContactStructure> contacts = new ArrayList<>();
    private int contactCount = 0;

    // To add the contact checks if name exists if not adds and checks again if name
    // is added correctly and return the string status
    public void addContact(String name, String number, String email) {
        contacts.add(new ContactStructure(name, number, email));
    }

    // Returns every contact in the list in a long String
    public String displayContacts() {
        String details = "";
        if (contacts.isEmpty())
            return "Empty Contact List";
        for (ContactStructure contact : contacts)
            details += contact.toString();
        return details + "\nEOF\n";
    }

    // Searches the list for contacts and returns entire contact else null
    public ContactStructure searchContact(String name, Boolean ExactMatch) {
        for (ContactStructure contact : contacts)
            if (contact.name == name)
                return contact;

        return null;
    }

    public ArrayList<ContactStructure> searchContact(String name) {
        ArrayList<ContactStructure> contactSearched = new ArrayList<>();
        for (ContactStructure contact : contacts)
            if (contact.name.contains(name))
                contactSearched.add(contact);

        return contactSearched;
    }

    // Searches the name in list and deletes if exists and returns the deleted
    // contact
    public ContactStructure deleteContact(String name) {
        ContactStructure contact = searchContact(name, true);
        if (contact != null)
            contacts.remove(contact);
        return contact;
    }

    public ContactStructure deleteContact(ContactStructure contact) {
        if (contact != null)
            contacts.remove(contact);
        return contact;
    }

    public boolean hasContacts() {
        return ((contacts.size()) > contactCount) ? true : false;
    }

    public ContactStructure getNextContact() {
        return (hasContacts()) ? contacts.get(contactCount++) : null;
    }

    public void setHascontacts() {
        contactCount = 0;
    }

    public int updateContact(ContactStructure contact, String name, String number, String email) {
        contact.setName(name);
        contact.setNumber(number);
        contact.setEmail(email);
        return 0;
    }
}
