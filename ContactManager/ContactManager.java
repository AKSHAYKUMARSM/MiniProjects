package AKSHAYKUMARSM.MiniProjects.ContactManager;

import  java.util.Arrays;

public class ContactManager {

    static class contactStruct{
        private String name;
        private String phone;
        private String email;

        public  contactStruct(String name,String phone, String email){
            this.name=name;
            this.phone=phone;
            this.email=email;
        }

        public String getName() {return name;}
        public String getEmail() {return email;}
        public String getPhone() {return phone;}

        public void setEmail(String email) {this.email = email;}
        public void setName(String name) {this.name = name;}
        public void setPhone(String phone) {this.phone = phone;}

        @Override
        public String toString(){
            String text="\nName\t:\t"+this.name+
                        "\nPhone\t:\t"+this.phone+
                        "\nEmail\t:\t"+this.email+"\n";

            return text;
        }

    }
    static class contactUtil{
        public static int countEntries(contactStruct[] array){
            return  (int) Arrays.stream(array).filter(obj->obj !=null).count();
        }
        
        public static void addContact(String name,String phone,String email){
            int size=countEntries(contacts);
            if(size<0)size=0;
            contacts[size]=new contactStruct(name, phone, email);

            System.out.println("Successfully Added a contact");
        }

        public static contactStruct search(String name){
            for (contactStruct n : contacts) {
                if(n.getName().compareTo(name)==0) return n;
            }
            return null;
        }

        public static void displayContact(String name){
            System.out.println(search(name).toString());
        }
        public static void displayContact(){
            for (contactStruct contact : contacts) {
                System.out.println(contact.toString());
            }
        }
        
    }

    static contactStruct[] contacts=new contactStruct[10];
    public static void main(String[] args) {
        
        contactUtil.addContact("Akshay", "8855664422", "akashay@gmail.com");
        contactUtil.addContact("Aryan", "6633559988", "arru@gmail.com");
        contactUtil.addContact("Eranna", "8899556644", "erarcr@ymail.com");

        contactUtil.displayContact("Eranna");
        contactUtil.displayContact("Aryan");
        contactUtil.displayContact();
        // try (Scanner sc = new Scanner(System.in)) {
        //     name=sc.nextLine();
        //     System.out.println("Hello World!"+name);
        // }        
    }

}

