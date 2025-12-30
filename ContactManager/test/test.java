package test;

import src.Manager.ContactManager;

public class test {
    public static void main(String[] args) {

        System.out.println("Test site for contact Manger:\n");
        ContactManager contactManager = new ContactManager();

        contactManager.addContact("Akash Sirvar", "96998", "alsdjjf");
        contactManager.addContact("Anil kumars TT", "5897465", "aldkfjlkdjf");
        contactManager.addContact("Asim", "987654321", "alsdjfkljdf");
        contactManager.addContact("Naveen", "9999999", null);
        System.out.println(contactManager.displayContacts());
        System.out.println(contactManager.searchContact("Asim", true));
        System.out.println(contactManager.deleteContact("Naveen"));

        System.out.println("\nIndividual Contact Details:\n");
        while (contactManager.hasContacts()) {
            System.err.println(contactManager.getNextContact());
        }

        System.out.println("Program exited");

    }
}
