package org.myApp.controller;

import org.myApp.model.CaesarCipher;
import org.myApp.model.FileService;

import java.io.IOException;

public class MainController {
    private final CaesarCipher caesarCipher;
    private final FileService fileService;

    public MainController() {
        this.caesarCipher = new CaesarCipher();
        this.fileService = new FileService();
    }

    public String processCommand(String command, String filePath, String key) {
        try {
            String text = fileService.readFile(filePath);

            switch (command.toUpperCase()) {
                case "ENCRYPT":
                    int encryptKey = Integer.parseInt(key);
                    boolean isUkrainianEncrypt = caesarCipher.isUkrainianText(text);
                    String encryptedText = caesarCipher.encrypt(text, encryptKey, isUkrainianEncrypt);
                    fileService.writeFile(filePath.replace(".txt", "[ENCRYPTED].txt"), encryptedText);
                    return "File encrypted successfully!";
                case "DECRYPT":
                    int decryptKey = Integer.parseInt(key);
                    boolean isUkrainianDecrypt = caesarCipher.isUkrainianText(text);
                    String decryptedText = caesarCipher.decrypt(text, decryptKey, isUkrainianDecrypt);
                    fileService.writeFile(filePath.replace(".txt", "[DECRYPTED].txt"), decryptedText);
                    return "File decrypted successfully!";
                case "BRUTE_FORCE":
                    caesarCipher.printAllDecryptions(text);
                    String bruteForceDecryptedText = caesarCipher.bruteForceDecrypt(text);
                    fileService.writeFile(filePath.replace(".txt", "[BRUTE_FORCED].txt"), bruteForceDecryptedText);
                    return "File brute force decrypted successfully!";
                default:
                    return "Unknown command: " + command;
            }
        } catch (IOException e) {
            return "Error reading or writing file: " + e.getMessage();
        } catch (NumberFormatException e) {
            return "Key must be a number: " + e.getMessage();
        }
    }
}