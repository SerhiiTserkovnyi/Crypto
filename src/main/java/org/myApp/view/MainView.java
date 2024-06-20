package org.myApp.view;

import org.myApp.controller.MainController;
import org.myApp.model.CaesarCipher;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainView {
    private JFrame frame;
    private JTextField filePathField;
    private JTextField keyField;
    private JTextArea messageArea;
    private JTextArea bruteForceResultsArea;
    private JComboBox<String> commandComboBox;
    private MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;
        initialize();
    }

    public MainView() {
        this.controller = new MainController();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Caesar Cipher");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(7, 1));

        filePathField = new JTextField();
        filePathField.setEditable(false);
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                filePathField.setText(file.getAbsolutePath());
            }
        });

        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(filePathField, BorderLayout.CENTER);
        filePanel.add(browseButton, BorderLayout.EAST);

        String[] commands = {"ENCRYPT", "DECRYPT", "BRUTE_FORCE"};
        commandComboBox = new JComboBox<>(commands);

        keyField = new JTextField();

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> {
            String command = (String) commandComboBox.getSelectedItem();
            String filePath = filePathField.getText();
            String key = keyField.getText();
            String message = controller.processCommand(command, filePath, key);
            messageArea.setText(message);
        });

        JButton bruteForceButton = new JButton("Brute Force Decrypt");
        bruteForceButton.addActionListener(e -> {
            String filePath = filePathField.getText();
            CaesarCipher cipher = new CaesarCipher();
            String bruteForceResults = cipher.bruteForceDecrypt(filePath);
            bruteForceResultsArea.setText(bruteForceResults);
        });

        messageArea = new JTextArea();
        messageArea.setEditable(false);

        bruteForceResultsArea = new JTextArea();
        bruteForceResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bruteForceResultsArea);

        frame.getContentPane().add(new JLabel("File Path:"));
        frame.getContentPane().add(filePanel);
        frame.getContentPane().add(new JLabel("Command:"));
        frame.getContentPane().add(commandComboBox);
        frame.getContentPane().add(new JLabel("Key:"));
        frame.getContentPane().add(keyField);
        frame.getContentPane().add(executeButton);
        frame.getContentPane().add(bruteForceButton);
        frame.getContentPane().add(new JScrollPane(messageArea));
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView window = new MainView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}