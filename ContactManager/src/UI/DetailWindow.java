package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.Manager.ContactManager;
import src.Manager.ContactStructure;

public class DetailWindow {
    ContactManager contactList = new ContactManager();
    String name, number, email;

    JFrame parentFrame;
    Home parentUI;
    JFrame mainFrame = new JFrame("Contact Details");
    JPanel headPanel = new JPanel(new BorderLayout());
    JPanel detailPanel = new JPanel(new GridLayout(3, 2, 5, 5));
    JButton cancelButton = new JButton("Cancel");
    JButton saveButton = new JButton("Save");
    JButton deleButton = new JButton("Delete");
    JLabel status = new JLabel("Details", JLabel.CENTER);

    JLabel nameLabel = new JLabel("Name :");
    JLabel numberLabel = new JLabel("Phone :");
    JLabel emailLabel = new JLabel("Email :");
    JTextField nameField = new JTextField(10);
    JTextField numberField = new JTextField(5);
    JTextField emailField = new JTextField(5);

    public DetailWindow(JFrame parentFrame, ContactStructure contact, Home parentUI) {
        this.parentFrame = parentFrame;
        this.parentUI = parentUI;
        parentFrame.setVisible(false);
        mainFrame.setAlwaysOnTop(true);
        if (contact != null) {
            name = contact.getName();
            number = contact.getNumber();
            email = contact.getEmail();
            headPanel.add(deleButton, BorderLayout.CENTER);

            deleButton.addActionListener(e -> {
                contactList.deleteContact(contact);
                closeWindow();
            });

            saveButton.addActionListener(e -> save(true, contact));
        } else {
            name = number = email = "";
            saveButton.addActionListener(e -> save(false, null));
        }

        nameField.setText(name);
        numberField.setText(number);
        emailField.setText(email);

        headPanel.add(status, BorderLayout.SOUTH);
        headPanel.add(cancelButton, BorderLayout.WEST);
        headPanel.add(saveButton, BorderLayout.EAST);
        detailPanel.add(nameLabel);
        detailPanel.add(nameField);
        detailPanel.add(numberLabel);
        detailPanel.add(numberField);
        detailPanel.add(emailLabel);
        detailPanel.add(emailField);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(headPanel, BorderLayout.NORTH);
        mainFrame.add(detailPanel, BorderLayout.SOUTH);
        mainFrame.setSize(400, 150);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(parentFrame);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        cancelButton.addActionListener(e -> {
            closeWindow();
        });

    }

    public void closeWindow() {
        mainFrame.dispose();
        parentFrame.setVisible(true);
        parentUI.refreshContacts();
    }

    public void save(boolean updateContact, ContactStructure contact) {
        String statuString;
        name = nameField.getText().trim();
        number = numberField.getText().trim();
        email = emailField.getText().trim();
        if (name.isBlank() || number.isBlank()) {
            status.setText("Please Enter Both name and number!");
            status.setForeground(Color.RED);
            return;
        }

        if (updateContact)
            statuString = contactList.updateContact(contact, name, number, email);
        else
            statuString = contactList.addContact(name, number, email);
        if (statuString == null)
            closeWindow();
        else {
            status.setText(statuString);
            status.setForeground(Color.RED);
            status.setBackground(Color.ORANGE);
        }
    }
}
