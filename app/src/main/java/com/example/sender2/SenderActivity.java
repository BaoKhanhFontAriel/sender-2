package com.example.sender2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SenderActivity extends AppCompatActivity {
    public static final String TAG = "SenderActivity";
    RecyclerView messageRecycler;
    SenderAdapter senderAdapter;
    Intent senderIntent;
    String[] senderMessages;
    BroadcastReceiver myReceiver;


    Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_layout);

        Button stopButton = findViewById(R.id.stopButton);
        messageRecycler = findViewById(R.id.MessageRecycler);
        TextView messageText = findViewById(R.id.messageText);
        TextView senderTitle = findViewById(R.id.senderTitle);

        myReceiver = new MyReceiver();


        senderIntent = new Intent("com.example.myMessage");
        senderIntent.setComponent(new ComponentName("com.example.receiver2", "com.example.receiver2.MyReceiver"));

        IntentFilter intentFilter = new IntentFilter("com.example.myMessage");
        registerReceiver(myReceiver, intentFilter);

        senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());

        messageRecycler.setAdapter(senderAdapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));


        // Send the message


        senderMessages = MainActivity.messages.split(";", 0);

        handler.postDelayed(runnable, 3000);

        processIntent(getIntent());

        stopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Message.getInstance().getMessageList().clear();
            senderAdapter.notifyDataSetChanged();
            count = 0;
            handler.removeCallbacks(runnable);
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void processIntent(Intent intent) {
        String message = intent.getStringExtra("receiver message");
        senderAdapter.insertItem(new MessageEntry("", message));

        scrollToCurrentPosition();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
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

            if (count < senderMessages.length) {
                sendMessage();
            } else {handler.removeCallbacks(runnable);}

            handler.postDelayed(this, 5000);
        }
    };

}
