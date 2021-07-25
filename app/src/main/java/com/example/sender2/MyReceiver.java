package com.example.sender2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent("update message");
        String message = intent.getStringExtra("receiver message");
//        Message.getInstance().addMessage(new MessageEntry("", message, false));

        i.putExtra("receiver message", message);
        context.sendBroadcast(i);
    }
}