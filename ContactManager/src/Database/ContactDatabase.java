package src.Database;

import src.Manager.ContactManager;

public class ContactDatabase {
    ContactManager contacts = new ContactManager();

    public ContactDatabase() {
        contacts.addContact("Akash Sirvar", "96998", "alsdjjf");
        contacts.addContact("Anil kumars TT", "5897465", "aldkfjlkdjf");
        contacts.addContact("Asim", "987654321", "alsdjfkljdf");
        contacts.addContact("Naveen", "9999999", null);
    }

}
