package org.myApp.view;

import org.myApp.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainView extends JFrame {
    private JTextField filePathField;
    private JComboBox<String> commandComboBox;
    private JTextField keyField;
    private JTextArea messageArea;
    private JButton executeButton;
    private JButton browseButton;
    private MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Caesar Cipher App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        JLabel filePathLabel = new JLabel("File Path:");
        filePathField = new JTextField();
        browseButton = new JButton("Browse...");
        inputPanel.add(filePathLabel);
        inputPanel.add(filePathField);
        inputPanel.add(new JLabel());
        inputPanel.add(browseButton);

        JLabel commandLabel = new JLabel("Command:");
        String[] commands = {"ENCRYPT", "DECRYPT", "BRUTE_FORCE"};
        commandComboBox = new JComboBox<>(commands);
        inputPanel.add(commandLabel);
        inputPanel.add(commandComboBox);

        JLabel keyLabel = new JLabel("Key:");
        keyField = new JTextField();
        inputPanel.add(keyLabel);
        inputPanel.add(keyField);

        add(inputPanel, BorderLayout.CENTER);

        executeButton = new JButton("Execute");
        add(executeButton, BorderLayout.SOUTH);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.NORTH);

        browseButton.addActionListener(e -> openFileChooser());

        executeButton.addActionListener(e -> executeCommand());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void executeCommand() {
        String command = (String) commandComboBox.getSelectedItem();
        String filePath = filePathField.getText();
        String key = keyField.getText();

        try {
            controller.processCommand(command, filePath, key);
            messageArea.setText("Operation completed successfully!");
        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView(new MainController()));
    }
}