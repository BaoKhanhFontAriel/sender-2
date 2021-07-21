package com.example.sender2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    SenderAdapter senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("")) {
            String message = intent.getStringExtra("receiver message");
            senderAdapter.insertItem(new MessageEntry("", message));
        }
    }
}