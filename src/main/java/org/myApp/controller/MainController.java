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

    public void processCommand(String command, String filePath, String key) {
        try {
            String text = fileService.readFile(filePath);

            switch (command.toUpperCase()) {
                case "ENCRYPT":
                    int encryptKey = Integer.parseInt(key);
                    boolean isUkrainianEncrypt = caesarCipher.isUkrainianText(text);
                    String encryptedText = caesarCipher.encrypt(text, encryptKey, isUkrainianEncrypt);
                    fileService.writeFile(filePath.replace(".txt", "[ENCRYPTED].txt"), encryptedText);
                    break;
                case "DECRYPT":
                    int decryptKey = Integer.parseInt(key);
                    boolean isUkrainianDecrypt = caesarCipher.isUkrainianText(text);
                    String decryptedText = caesarCipher.decrypt(text, decryptKey, isUkrainianDecrypt);
                    fileService.writeFile(filePath.replace(".txt", "[DECRYPTED].txt"), decryptedText);
                    break;
                case "BRUTE_FORCE":
                    String bruteForceDecryptedText = caesarCipher.bruteForceDecrypt(text);
                    fileService.writeFile(filePath.replace(".txt", "[BRUTE_FORCED].txt"), bruteForceDecryptedText);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command: " + command);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading or writing file: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Key must be a number: " + e.getMessage(), e);
        }
    }
}