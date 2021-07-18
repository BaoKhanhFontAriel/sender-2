package com.example.sender2;

import java.util.ArrayList;

public class Message {

    private static Message instance;
    private ArrayList<String> messageList;

    public static Message getInstance() {
        if (instance == null){
            instance = new Message();
        }
        return instance;
    }

    public void addMessage(String message){
        messageList.add(message);
    }

    public ArrayList<String> getMessageList() {
        return messageList;
    }
}
