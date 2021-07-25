package com.example.sender2;

import android.util.Log;

public class MessageEntry {
    private String senderMessage;
    private String receiverMessage;
    private Boolean isWaitingResponse;

    public MessageEntry(String sender, String receiver, Boolean isWaitingResponse) {
        this.receiverMessage = receiver;
        this.senderMessage = sender;
        this.isWaitingResponse = isWaitingResponse;
    }

    public String getSenderMessage() {
            return senderMessage;
    }

    public String getReceiverMessage() {
            return receiverMessage;
    }

    public Boolean getIsWaitingResponse(){
        return isWaitingResponse;
    }
}
