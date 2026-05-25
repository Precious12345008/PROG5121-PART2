/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.createaccount;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NewClass1Test {

    // -----------------------------
    // LOGIN LOGIC TESTS
    // -----------------------------

    @Test
    public void testCorrectLoginValues() {

        NewClass1 app = new NewClass1();

        String username = "user123";
        String password = "pass123";

        boolean result =
                username.equals(app.correctUsername)
                && password.equals(app.correctPassword);

        assertTrue(result);
    }

    @Test
    public void testWrongUsername() {

        NewClass1 app = new NewClass1();

        String username = "wrong";
        String password = "pass123";

        boolean result =
                username.equals(app.correctUsername)
                && password.equals(app.correctPassword);

        assertFalse(result);
    }

    @Test
    public void testWrongPassword() {

        NewClass1 app = new NewClass1();

        String username = "user123";
        String password = "wrong";

        boolean result =
                username.equals(app.correctUsername)
                && password.equals(app.correctPassword);

        assertFalse(result);
    }

    // -----------------------------
    // MESSAGE CLASS TESTS
    // -----------------------------

    @Test
    public void testValidRecipient() {

        Message msg =
                new Message("+27831234567", "Hello");

        assertEquals(
                "Cellphone number successfully captured.",
                msg.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipient() {

        Message msg =
                new Message("0831234567", "Hello");

        assertTrue(
                msg.checkRecipientCell()
                        .contains("incorrectly formatted")
        );
    }

    @Test
    public void testMessageLengthValid() {

        Message msg =
                new Message("+27831234567", "Short message");

        assertEquals(
                "Message ready to send.",
                msg.checkMessageLength()
        );
    }

    @Test
    public void testMessageTooLong() {

        String longMsg = "A".repeat(260);

        Message msg =
                new Message("+27831234567", longMsg);

        assertTrue(
                msg.checkMessageLength()
                        .contains("exceeds 250 characters")
        );
    }

    @Test
    public void testMessageIDExists() {

        Message msg =
                new Message("+27831234567", "Test");

        assertNotNull(msg.messageID);
        assertEquals(10, msg.messageID.length());
    }

    @Test
    public void testSendMessage() {

        Message msg =
                new Message("+27831234567", "Test message");

        assertEquals(
                "Message successfully sent.",
                msg.sendMessage()
        );
    }

    @Test
    public void testTotalMessagesIncrease() {

        int before = Message.returnTotalMessages();

        Message msg =
                new Message("+27831234567", "Another message");

        msg.sendMessage();

        int after = Message.returnTotalMessages();

        assertEquals(before + 1, after);
    }

    @Test
    public void testStoreMessage() {

        Message msg =
                new Message("+27831234567", "Store test");

        assertEquals(
                "Message successfully stored",
                msg.storeMessage()
        );
    }

    @Test
    public void testDisregardMessage() {

        Message msg =
                new Message("+27831234567", "Delete test");

        assertEquals(
                "Press 0 to delete the message",
                msg.disregardMessage()
        );
    }
}