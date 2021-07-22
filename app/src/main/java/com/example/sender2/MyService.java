package com.example.sender2;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyService extends Service {

    RecyclerView messageRecycler;
    SenderAdapter senderAdapter;
    Intent senderIntent;
    String[] senderMessages;
    BroadcastReceiver myReceiver;
    Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myReceiver = new MyReceiver();


        senderIntent = new Intent("com.example.myMessage");
        senderIntent.setComponent(new ComponentName("com.example.receiver2", "com.example.receiver2.MyReceiver"));

        IntentFilter intentFilter = new IntentFilter("com.example.myMessage");
        registerReceiver(myReceiver, intentFilter);


        senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());

        messageRecycler.setAdapter(senderAdapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));


        senderMessages = MainActivity.messages.split(";", 0);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
        new Thread(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private int count = 0;

    public void sendMessage() {
        senderIntent.putExtra("sender message", senderMessages[count]);

        senderAdapter.insertItem(new MessageEntry(senderMessages[count], ""));

        sendBroadcast(senderIntent);

        scrollToCurrentPosition();
        count++;
    }

    public void scrollToCurrentPosition() {
        messageRecycler.scrollToPosition(Message.getInstance().getMessageList().size()- 1);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count < senderMessages.length) {
                sendMessage();
            } else {handler.removeCallbacks(runnable);}


        }
    };
}