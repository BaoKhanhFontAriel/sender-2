package com.example.sender2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(SenderActivity.TAG, "intent: " + intent.getAction());
        if (!intent.getAction().equals("")) {
            Intent i = new Intent(context, SenderActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("receiver message", intent.getStringExtra("receiver message"));
            context.startActivity(i);
        }
    }
}