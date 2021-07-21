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
    private static final String TAG = "SenderActivity";
    RecyclerView messageRecycler;
    SenderAdapter senderAdapter;
    Intent senderIntent;
    String[] senderMessages;
    MyReceiver myReceiver;


    Handler handler = new Handler(Looper.getMainLooper());

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "intent: " + intent.getAction());
            if (intent.getAction().equals("")) {

                String message = intent.getStringExtra("receiver message");
                senderAdapter.insertItem(new MessageEntry("", "hello"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_layout);

        Button stopButton = findViewById(R.id.stopButton);
        messageRecycler = findViewById(R.id.MessageRecycler);
        TextView messageText = findViewById(R.id.messageText);
        TextView senderTitle = findViewById(R.id.senderTitle);

        senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());

        messageRecycler.setAdapter(senderAdapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Send the message
        senderIntent = new Intent("com.example.myMessage2222");
        senderIntent.setComponent(new ComponentName("com.example.receiver2", "com.example.receiver2.MainActivity"));


        senderMessages = MainActivity.messages.split(";", 0);

        handler.postDelayed(runnable, 3000);


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
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.example.MessageFilter");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    private int count = 0;

    public void sendMessage() {
        senderIntent.putExtra("sender message", senderMessages[count]);
        Log.d(TAG, "sendMessage: " + senderMessages[count]);
//        senderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(senderIntent);

        senderAdapter.insertItem(new MessageEntry(senderMessages[count], ""));
        messageRecycler.scrollToPosition(count);
        count++;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count < senderMessages.length) {
                sendMessage();
            }
            handler.postDelayed(this, 1000);
        }
    };

}
