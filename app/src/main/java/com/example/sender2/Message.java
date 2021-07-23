package com.example.sender2;

import java.util.ArrayList;

public class Message {

    private static Message instance;
    private ArrayList<MessageEntry> messageList = new ArrayList<>();

    public static Message getInstance() {
        if (instance == null){
            instance = new Message();
        }
        return instance;
    }

    public void addMessage(MessageEntry message){
        messageList.add(message);
    }

    public ArrayList<MessageEntry> getMessageList() {
        return messageList;
    }

    public MessageEntry getLastMessage(){
        return messageList.get(messageList.size() - 1);
    }
}
