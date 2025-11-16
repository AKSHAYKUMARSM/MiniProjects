package AKSHAYKUMARSM.MiniProjects.ContactManager;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManager {

    static class structContact {
        private String name;
        private String phone;
        private String email;

        public structContact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            String text = "\nName\t:\t" + this.name +
                    "\nPhone\t:\t" + this.phone +
                    "\nEmail\t:\t" + this.email + "\n";

            return text;
        }

    }

    static class utilContact {
        public static int countEntries(structContact[] array) {
            return (int) Arrays.stream(array).filter(obj -> obj != null).count();
        }

        public static void addContact(String name, String phone, String email) {
            if (!validateName(name) || !validatePhone(phone) || !validateEmail(email)) {
                System.out.println("Invalid Details!");
                return;
            }
            int size = countEntries(contacts);
            if (size < 0)
                size = 0;
            contacts[size] = new structContact(name, phone, email);

            System.out.println("Successfully Added a contact");
        }

        public static structContact search(String name) {
            if (!validateName(name))
                return null;
            for (structContact n : contacts) {
                if (n.getName().compareTo(name) == 0)
                    return n;
            }
            return null;
        }

        public static void displayContact(String name) {
            if (search(name) == null) {
                System.out.println("Name not found!");
                return;
            }
            System.out.println("======== Contact Found ============");
            System.out.println(search(name).toString());
            System.out.println("======== END ============");
        }

        public static void displayContact() {
            for (structContact contact : contacts) {
                if (contact == null) {
                    System.out.println("============ End Of Contacts =============");
                    return;
                }
                System.out.println(contact.toString());
            }
            System.out.println("============ End Of Contacts =============");
        }

        static boolean validateName(String name) {
            String regex = "^[A-Z].{2,20}";
            Matcher match = Pattern.compile(regex).matcher(name);
            return match.find();
        }

        static boolean validatePhone(String phone) {
            String regex = "^[0-9*#]{1,12}$";
            Matcher match = Pattern.compile(regex).matcher(phone);
            return match.find();
        }

        static boolean validateEmail(String email) {
            String regex = "^[a-z$!].{2,}@[a-z0-9.-]+[.][a-z]{2,}$";
            Matcher match = Pattern.compile(regex).matcher(email);
            return match.find();
        }

    }

    static structContact[] contacts = new structContact[10];

    public static void main(String[] args) {

        utilContact.addContact("Akshay", "8855664422", "akashay@gmail.com");
        utilContact.addContact("Aryan", "6633559988", "arru@gmail.com");
        utilContact.addContact("Eranna", "8899556644", "erarcr@ymail.com");
        utilContact.addContact("a", "8855", "aru@gmail.com");
        utilContact.addContact("arun", "8877", "aru@gmail.com");
        utilContact.addContact("Arun", "8", "aru@gmail.com");
        utilContact.addContact("Revanna", "8855", "argmail.com");

        utilContact.displayContact("Eranna");
        utilContact.displayContact("Aryan");
        utilContact.displayContact();

        // try (Scanner sc = new Scanner(System.in)) {
        // name=sc.nextLine();
        // System.out.println("Hello World!"+name);
        // }
    }

}
