package test;

import src.Database.ContactDatabase;
import src.Manager.ContactManager;
import src.Manager.ContactStructure;

public class test {
    public static void main(String[] args) {

        new ContactDatabase();
        ContactManager cm = new ContactManager();
        ContactStructure newcontact = new ContactStructure(0, "Mevalid", "9874", null);
        if (!cm.searchContact("ka").isEmpty())
            newcontact = cm.searchContact("ka").get(0);
        else
            newcontact = null;
        System.out.println(cm);
        System.out.println(newcontact);
        System.out.println(cm.deleteContact(newcontact));

        System.out.println("Program exited");

    }
}
