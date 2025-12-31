package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import src.Manager.ContactManager;
import src.Manager.ContactStructure;

public class Home {
    ContactManager contactManager = new ContactManager();
    JFrame rootFrame = new JFrame("Contact Manager");
    JPanel headPanel = new JPanel(new BorderLayout());
    JPanel searchPanel = new JPanel(new FlowLayout());
    JPanel navigationPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel searchingPanel = new JPanel();

    JLabel head = new JLabel("Contacts", SwingConstants.CENTER);
    JButton addContactButton = new JButton("Add to Contact");
    JButton refreshButton = new JButton("Refresh");
    JButton settingButton = new JButton("Settings");
    JButton searchContactButton = new JButton("Search");
    JButton exiButton = new JButton("Exit");
    JTextField searchField = new JTextField(15);
    ArrayList<JTextArea> contactDisplayArea = new ArrayList<>();

    public Home() {
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setLocationRelativeTo(null);

        refreshButton.addActionListener(e -> refreshContacts());
        addContactButton.addActionListener(e -> detailMenu(null));
        settingButton.addActionListener(e -> new SettingWindow(rootFrame, this));
        exiButton.addActionListener(e -> System.exit(0));
        searchContactButton.addActionListener(e -> searchContact(searchField.getText()));

        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.add(settingButton);
        navigationPanel.add(addContactButton);
        navigationPanel.add(exiButton);

        searchPanel.add(searchField);
        searchPanel.add(searchContactButton);

        headPanel.setBackground(Color.CYAN);
        headPanel.add(head, BorderLayout.CENTER);
        headPanel.add(refreshButton, BorderLayout.EAST);
        headPanel.add(searchPanel, BorderLayout.SOUTH);

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        searchingPanel.setLayout(new BoxLayout(searchingPanel, BoxLayout.Y_AXIS));

        refreshContacts();
        // displayPanel.add(new JTextArea(contactManager.displayContacts()));

        rootFrame.setLayout(new BorderLayout());
        rootFrame.add(headPanel, BorderLayout.NORTH);
        rootFrame.add(displayPanel, BorderLayout.CENTER);
        rootFrame.add(navigationPanel, BorderLayout.SOUTH);

        rootFrame.setVisible(true);
        rootFrame.pack();
    }

    public void refreshContacts() {
        refreshButton.setText("Refresh");
        searchField.setText(null);
        displayPanel.removeAll();
        contactDisplayArea.clear();
        if (contactManager.getContacts().isEmpty()) {
            displayPanel.add(new JTextArea("Empty Contact List!"));
            return;
        }

        for (ContactStructure contact : contactManager.getContacts()) {
            JTextArea newContact = new JTextArea(contact.getName() + "\n\t" + contact.getNumber());
            displayPanel.add(newContact);
            newContact.setBackground(rootFrame.getContentPane().getBackground());
            newContact.setForeground(newContact.getBackground()
                    .equals(Color.BLACK) ? Color.WHITE : Color.BLACK);

            newContact.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    detailMenu(contact);
                }
            });
        }
        rootFrame.remove(searchingPanel);
        rootFrame.add(displayPanel);
        displayPanel.revalidate();
        rootFrame.repaint();
        rootFrame.pack();
    }

    public void detailMenu(ContactStructure contact) {
        new DetailWindow(rootFrame, contact, this);
    }

    public void searchContact(String nameOrPhone) {
        rootFrame.getContentPane().remove(displayPanel);
        refreshButton.setText("Cancel Search");
        rootFrame.add(searchingPanel, BorderLayout.CENTER);
        searchingPanel.removeAll();
        if (contactManager.searchContact(nameOrPhone) == null || contactManager.searchContact(nameOrPhone).isEmpty())
            return;
        for (ContactStructure contact : contactManager.searchContact(nameOrPhone)) {
            JTextArea newContact = new JTextArea(contact.getName() + "\n\t" + contact.getNumber());
            searchingPanel.add(newContact);
            newContact.setBackground(rootFrame.getContentPane().getBackground());
            newContact.setForeground(newContact.getBackground()
                    .equals(Color.BLACK) ? Color.WHITE : Color.BLACK);

            newContact.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    detailMenu(contact);
                }
            });
        }
        searchingPanel.repaint();
        searchingPanel.revalidate();
        rootFrame.repaint();
    }

    public void changeTheme(Color b) {
        if (b.equals(Color.BLACK)) {
            rootFrame.getContentPane().setForeground(Color.WHITE);
        } else {
            rootFrame.getContentPane().setForeground(Color.BLACK);
        }
        rootFrame.getContentPane().setBackground(b);
        refreshContacts();
    }

}
