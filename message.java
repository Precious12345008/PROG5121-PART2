package com.mycompany.createaccount;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.*;

public class Message {

    String messageID;
    String recipient;
    String message;
    String messageHash;

    static int totalSent = 0;
    static JSONArray sentMessagesJSON = new JSONArray();

    private static final String JSON_FILE = "messages.json";

    public Message(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.messageID = createMessageID();
        this.messageHash = createMessageHash();
    }

    public String createMessageID() {
        long randomNum = (long)(Math.random() * 9000000000L + 1000000000L);
        return "" + randomNum;
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+")) {
            return "Cellphone number successfully captured.";
        } else if (recipient.startsWith("0") && recipient.length() <= 10) {
            return "Cellphone number successfully captured.";
        } else {
            return "Cellphone number is incorrectly formatted or does not contain "
                    + "an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = message.length() - 250;
            return "Message exceeds 250 characters by " + extra + ", please reduce the size.";
        }
    }

    public String createMessageHash() {
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String hash = messageID.substring(0, 2) + ":" + totalSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String sendMessage() {
        totalSent++;
        JSONObject msgObj = new JSONObject();
        msgObj.put("id", messageID);
        msgObj.put("hash", messageHash);
        msgObj.put("recipient", recipient);
        msgObj.put("message", message);
        msgObj.put("status", "sent");
        sentMessagesJSON.put(msgObj);
        saveMessagesToFile();
        return "Message successfully sent.";
    }

    public static void saveMessagesToFile() {
        try (FileWriter fw = new FileWriter(JSON_FILE)) {
            fw.write(sentMessagesJSON.toString(2));
            System.out.println("[JSON] Messages saved to " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("[JSON] Error saving: " + e.getMessage());
        }
    }

    public static void loadMessagesFromFile() {
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            System.out.println("[JSON] No saved messages found. Starting fresh.");
            return;
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
            sentMessagesJSON = new JSONArray(content);
            totalSent = sentMessagesJSON.length();
            System.out.println("[JSON] Loaded " + totalSent + " message(s).");
        } catch (IOException e) {
            System.out.println("[JSON] Error loading: " + e.getMessage());
        }
    }

    public String disregardMessage() {
        return "Message disregarded.";
    }

    public String storeMessage() {
        JSONObject msgObj = new JSONObject();
        msgObj.put("id", messageID);
        msgObj.put("hash", messageHash);
        msgObj.put("recipient", recipient);
        msgObj.put("message", message);
        msgObj.put("status", "stored");
        sentMessagesJSON.put(msgObj);
        saveMessagesToFile();
        return "Message successfully stored.";
    }

    public static String printMessages() {
        if (sentMessagesJSON.length() == 0) {
            return "No messages have been sent.";
        }
        StringBuilder result = new StringBuilder("\n--- All Sent Messages ---\n\n");
        for (int i = 0; i < sentMessagesJSON.length(); i++) {
            JSONObject obj = sentMessagesJSON.getJSONObject(i);
            result.append((i + 1)).append(". ")
                  .append("ID: ").append(obj.getString("id"))
                  .append(" | Hash: ").append(obj.getString("hash"))
                  .append(" | Recipient: ").append(obj.getString("recipient"))
                  .append(" | Message: ").append(obj.getString("message"))
                  .append(" | Status: ").append(obj.getString("status"))
                  .append("\n");
        }
        return result.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    public void displayDetails() {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID   : " + messageID);
        System.out.println("Message Hash : " + messageHash);
        System.out.println("Recipient    : " + recipient);
        System.out.println("Message      : " + message);
        System.out.println("----------------------");
    }
}