package org.myApp;

import org.myApp.controller.MainController;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2 || (args.length < 3 && !args[0].equalsIgnoreCase("BRUTE_FORCE"))) {
            System.err.println("Usage: java -jar myApp.jar <command> <filePath> <key>");
            System.exit(1);
        }

        String command = args[0];
        String filePath = args[1];
        String key = args.length > 2 ? args[2] : "";

        MainController controller = new MainController();
        controller.processCommand(command, filePath, key);
    }
}