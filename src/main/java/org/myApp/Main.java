package org.myApp;

import org.myApp.view.MainView;
import org.myApp.controller.MainController;

public class Main {
    public static void main(String[] args) {
        new MainView(new MainController());
    }
}