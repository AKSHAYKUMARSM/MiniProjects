package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class SettingWindow {
    Home parentUI;
    Color themeColor;
    JFrame parentFrame;
    JFrame mainFrame = new JFrame("Settings");
    JLabel themeLabel = new JLabel("Theme");
    JRadioButton lightButton = new JRadioButton("Light");
    JRadioButton darkButton = new JRadioButton("Dark");

    JButton backButton = new JButton("Back");
    JTextArea abouTextArea = new JTextArea("About\n\nThis is a simple contact manager program.\nVersion: 1.0.0v");

    public SettingWindow(JFrame parentFrame, Home parentUI) {
        this.parentFrame = parentFrame;
        this.parentUI = parentUI;
        parentFrame.setVisible(false);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        mainFrame.add(backButton, BorderLayout.NORTH);
        mainFrame.add(panel, BorderLayout.CENTER);

        JPanel flowPanel = new JPanel(new FlowLayout());
        panel.add(flowPanel, BorderLayout.NORTH);
        panel.add(abouTextArea, BorderLayout.CENTER);

        flowPanel.add(themeLabel);
        flowPanel.add(lightButton);
        flowPanel.add(darkButton);

        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightButton);
        themeGroup.add(darkButton);

        lightButton.addActionListener(e -> parentUI.changeTheme(Color.WHITE));
        darkButton.addActionListener(e -> parentUI.changeTheme(Color.BLACK));
        if (parentFrame.getContentPane().getBackground().equals(Color.BLACK))
            darkButton.setSelected(true);
        else
            lightButton.setSelected(true);

        mainFrame.setSize(400, 250);
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

        backButton.addActionListener(e -> {
            closeWindow();
        });
    }

    public void closeWindow() {
        mainFrame.dispose();
        parentFrame.setVisible(true);
    }

}
