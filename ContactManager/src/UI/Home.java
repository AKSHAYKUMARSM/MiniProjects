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
    JPanel navigationPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel searchPanel = new JPanel(new FlowLayout());

    JLabel head = new JLabel("Contact Manager", SwingConstants.CENTER);
    JButton addContactButton = new JButton("Add to Contact");
    JButton refreshButton = new JButton("Refresh");
    JButton settingButton = new JButton("Settings");
    JButton searchContactButton = new JButton("Search");
    JButton exiButton = new JButton("Exit");
    JTextField searchField = new JTextField(15);
    ArrayList<JTextArea> contactTextArea = new ArrayList<>();

    public Home() {
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
        rootFrame.setSize(600, 750);
        rootFrame.setLocationRelativeTo(null);

        refreshButton.addActionListener(e -> refreshContacts());
        addContactButton.addActionListener(e -> detailMenu(null));
        settingButton.addActionListener(e -> new SettingWindow(rootFrame, this));
        exiButton.addActionListener(e -> System.exit(0));
        searchContactButton.addActionListener(e -> searchContact(searchField.getText()));

        headPanel.setBackground(Color.CYAN);

        headPanel.add(head, BorderLayout.CENTER);
        headPanel.add(refreshButton, BorderLayout.EAST);

        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));

        navigationPanel.add(settingButton);
        navigationPanel.add(addContactButton);
        navigationPanel.add(exiButton);
        searchPanel.add(searchField);
        searchPanel.add(searchContactButton);
        headPanel.add(searchPanel, BorderLayout.SOUTH);

        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

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
        displayPanel.removeAll();
        contactTextArea.removeAll(contactTextArea);

        while (contactManager.hasContacts()) {
            ContactStructure contact = contactManager.getNextContact();
            JTextArea newContaButton = new JTextArea(contact.getName() + "\n\t" + contact.getNumber());
            contactTextArea.add(newContaButton);
            displayPanel.add(contactTextArea.getLast());
            newContaButton.setBackground(rootFrame.getContentPane().getBackground());
            newContaButton
                    .setForeground(newContaButton.getBackground().equals(Color.BLACK) ? Color.WHITE : Color.BLACK);
            contactTextArea.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    detailMenu(contact);
                }
            });
        }
        contactManager.setHascontacts();
        displayPanel.revalidate();
        rootFrame.pack();
        // displayPanel.repaint();
    }

    public void detailMenu(ContactStructure contact) {
        new DetailWindow(rootFrame, contact, this);
    }

    public void searchContact(String name) {
        if (name.isEmpty())
            return;
        displayPanel.removeAll();
        contactTextArea.removeAll(contactTextArea);
        for (ContactStructure contact : contactManager.searchContact(name)) {
            JTextArea newContaButton = new JTextArea(contact.getName() + "\n\t" +
                    contact.getNumber());
            contactTextArea.add(newContaButton);
            displayPanel.add(contactTextArea.getLast());
            newContaButton.setBackground(rootFrame.getContentPane().getBackground());
            newContaButton
                    .setForeground(newContaButton.getBackground().equals(Color.BLACK) ? Color.WHITE : Color.BLACK);
            contactTextArea.getLast().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    detailMenu(contact);
                }
            });
        }
        JTextArea endofTextArea = new JTextArea("End of Search");
        displayPanel.add(endofTextArea);

        displayPanel.revalidate();
        rootFrame.pack();
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
