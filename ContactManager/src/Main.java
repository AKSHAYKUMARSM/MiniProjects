package src;

import src.Database.ContactDatabase;
import src.UI.Home;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to first project!");
        new ContactDatabase();

        new Home();

    }
}
