package com.example.sender2;

import android.util.Log;

public class MessageEntry {
    String senderMessage;
    String receiverMessage;

    public MessageEntry(String sender, String receiver) {
        this.receiverMessage = receiver;
        this.senderMessage = sender;
    }

    public String getSenderMessage() {

        if (senderMessage != null) {
            return senderMessage;
        }
        return "";
    }

    public String getReceiverMessage() {
        if (receiverMessage != null) {
            return receiverMessage;
        }
        return "";
    }

}
