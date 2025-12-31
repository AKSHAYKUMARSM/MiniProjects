package src.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import src.Manager.ContactStructure;

public class ContactDatabase {
    private final String DBurl = "jdbc:sqlite:contacts.db";
    private Connection DBconn;

    private boolean initDB() {
        try {
            Connection conn = DriverManager.getConnection(DBurl);
            this.DBconn = conn;
            setupTable(DBconn);
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to database!" + e.getMessage());
        }
        return false;
    }

    private boolean setupTable(Connection DBconn) throws SQLException {
        final String tablesql = """
                CREATE TABLE IF NOT EXISTS contacts(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                phone TEXT,
                email TEXT
                )
                """;

        try (Statement createTable = DBconn.createStatement()) {
            createTable.execute(tablesql);
            return true;
        }
    }

    public ContactDatabase() {
        initDB();
    }

    public boolean addContact(String name, String phone, String email) {
        String insertSql = "INSERT INTO contacts(name,phone,email) VALUES(?,?,?)";

        try (PreparedStatement insertStatement = DBconn.prepareStatement(insertSql)) {
            insertStatement.setString(1, name);
            insertStatement.setString(2, phone);
            insertStatement.setString(3, email == null ? null : email.isBlank() ? null : email);
            insertStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<ContactStructure> fetchContacts() {
        String selectSql = "SELECT * FROM contacts ORDER BY name LIMIT 10";
        ArrayList<ContactStructure> contacts = new ArrayList<>();

        try (Statement selecStatement = DBconn.createStatement();
                ResultSet DBresult = selecStatement.executeQuery(selectSql)) {
            while (DBresult.next()) {
                contacts.add(new ContactStructure(
                        DBresult.getInt("id"),
                        DBresult.getString("name"),
                        DBresult.getString("phone"),
                        DBresult.getString("email")));
            }
            return contacts;
        } catch (SQLException e) {
            System.out.println("Error while fetching data :" + e.getMessage());
        }
        return null;
    }

    public ContactStructure searchContact(int id) {
        String searchSql = "SELECT * FROM contacts WHERE id=(?)";
        try (PreparedStatement searchStatement = DBconn.prepareStatement(searchSql)) {
            searchStatement.setInt(1, id);
            try (ResultSet searchResult = searchStatement.executeQuery()) {
                if (searchResult.next())
                    return new ContactStructure(
                            searchResult.getInt("id"),
                            searchResult.getString("name"),
                            searchResult.getString("phone"),
                            searchResult.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error searching contact: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<ContactStructure> searchContacts(String nameORnumber) {
        ArrayList<ContactStructure> searchedContacts = new ArrayList<>();
        String searchSql = "SELECT * FROM contacts WHERE name LIKE ? OR phone LIKE ? ORDER BY name";
        try (PreparedStatement searchStatement = DBconn.prepareStatement(searchSql)) {
            searchStatement.setString(1, "%" + nameORnumber + "%");
            searchStatement.setString(2, "%" + nameORnumber + "%");
            try (ResultSet searchResult = searchStatement.executeQuery()) {
                while (searchResult.next()) {
                    searchedContacts.add(new ContactStructure(
                            searchResult.getInt("id"),
                            searchResult.getString("name"),
                            searchResult.getString("phone"),
                            searchResult.getString("email")));
                }

                return searchedContacts;
            }

        } catch (SQLException e) {
            System.out.println("Error searching contact: " + e.getMessage());
        }
        return null;
    }

    public boolean deleteContact(int id) {
        String deletsql = "DELETE FROM contacts WHERE id=?";

        try (PreparedStatement statement = DBconn.prepareStatement(deletsql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() > 0)
                return true;

        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());

        }
        return false;
    }

    public boolean updateContact(int id, String name, String phone, String email) {
        String updatesql = "UPDATE contacts SET name=?,phone=?,email=? WHERE id=?";

        try (PreparedStatement updateStatment = DBconn.prepareStatement(updatesql)) {
            updateStatment.setString(1, name);
            updateStatment.setString(2, phone);
            updateStatment.setString(3, email == null ? null : email.isBlank() ? null : email);
            updateStatment.setInt(4, id);
            if (updateStatment.executeUpdate() > 0)
                return true;
        } catch (Exception e) {
            System.out.println("Error while updating: " + e.getMessage());
        }
        return false;
    }

}
