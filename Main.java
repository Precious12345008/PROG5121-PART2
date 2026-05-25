package com.mycompany.createaccount;

public class Main {
    public static void main(String[] args) {
        NewClass1 app = new NewClass1();
        if (app.login()) {
            app.runMenu();
        }
    }
}