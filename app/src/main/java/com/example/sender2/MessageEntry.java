package com.example.sender2;

public class MessageEntry {
    String senderMessage;
    String receiverMessage;

    public MessageEntry(String sender, String receiver){
        this.receiverMessage = sender;
        this.senderMessage = receiver;
    }

    public String getSenderMessage(){ return senderMessage;}

    public String getReceiverMessage(){ return receiverMessage;}

}
