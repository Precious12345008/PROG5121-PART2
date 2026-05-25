package com.mycompany.createaccount;

import java.util.Scanner;

public class NewClass1 {

    Scanner scanner = new Scanner(System.in);
    String correctUsername = "user123";
    String correctPassword = "pass123";
    boolean loggedIn = false;
    int limit = 0;
    int count = 0;

    public boolean login() {
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("==============================\n");
            System.out.print("************Login*************\n");
            System.out.print("==============================\n");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                loggedIn = true;
                System.out.println("Login successful!");
                return true;
            }
            System.out.println("Incorrect credentials. Try again.");
        }
        System.out.println("Too many failed attempts. Exiting.");
        return false;
    }

    public void runMenu() {
        Message.loadMessagesFromFile();

        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages would you like to send? ");
        limit = scanner.nextInt();
        scanner.nextLine();

        boolean running = true;

        while (running) {
            System.out.print("************Menu*************\n");
            System.out.println("\n1) Send Messages  \n2) Recently Sent  \n3) Quit");
            System.out.print("Choose: ");
            String option = scanner.nextLine();

            if (option.equals("1")) {

                if (count >= limit) {
                    System.out.println("Message limit reached.");
                    continue;
                }

                System.out.print("Recipient number: ");
                String recipient = scanner.nextLine();
                System.out.print("Message: ");
                String text = scanner.nextLine();

                Message msg = new Message(recipient, text);

                String recipientCheck = msg.checkRecipientCell();
                System.out.println(recipientCheck);
                if (!recipientCheck.equals("Cellphone number successfully captured.")) {
                    continue;
                }

                String lengthCheck = msg.checkMessageLength();
                System.out.println(lengthCheck);
                if (!lengthCheck.equals("Message ready to send.")) {
                    continue;
                }

                System.out.println("Message ID: " + msg.messageID);
                System.out.println("Message Hash: " + msg.messageHash);

                System.out.println("\n1) Send  \n2) Delete  \n3) Store");
                System.out.print("Choose: ");
                String action = scanner.nextLine();

                if (action.equals("1")) {
                    System.out.println(msg.sendMessage());
                    msg.displayDetails();
                    count++;
                } else if (action.equals("2")) {
                    System.out.println(msg.disregardMessage());
                } else if (action.equals("3")) {
                    System.out.println(msg.storeMessage());
                } else {
                    System.out.println("Invalid option.");
                }

            } else if (option.equals("2")) {
                System.out.println(Message.printMessages());

            } else if (option.equals("3")) {
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                System.out.println(Message.printMessages());
                System.out.println("Goodbye!");
                running = false;

            } else {
                System.out.println("Invalid option, please choose 1, 2, or 3.");
            }
        }
    }
}