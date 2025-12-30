package src.Manager;

//Structure that shows what each contact contains
public class ContactStructure {
    String name;
    String number;
    String email;

    public ContactStructure(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Name: \t" + this.name + "\nNumber: \t" + this.number + "\nEmail: \t" + this.email + "\n";
    }
}
