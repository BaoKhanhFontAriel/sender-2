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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SenderActivity extends AppCompatActivity {
    public static final String TAG = "SenderActivity";
    RecyclerView messageRecycler;
    SenderAdapter senderAdapter;
    Intent senderIntent;
    String[] senderMessages;
    boolean isMessageSendImmediately = false;


    Handler handler = new Handler(Looper.getMainLooper());

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");

            senderAdapter.insertItem(Message.getInstance().getLastMessage());
            scrollToCurrentPosition();

            Log.d(TAG, "onReceive: immediateRunnable call: " + handler.post(immediateRunnable));

            isMessageSendImmediately = true;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_layout);


        Log.d(TAG, "onCreate: ");

        Button stopButton = findViewById(R.id.stopButton);
        messageRecycler = findViewById(R.id.MessageRecycler);
        TextView messageText = findViewById(R.id.messageText);
        TextView senderTitle = findViewById(R.id.senderTitle);


        senderIntent = new Intent("com.example.myMessage");
        senderIntent.setComponent(new ComponentName("com.example.receiver2", "com.example.receiver2.MyReceiver"));

        IntentFilter intentFilter = new IntentFilter("com.example.myMessage");
        registerReceiver(myReceiver, intentFilter);

        senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());

        messageRecycler.setAdapter(senderAdapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));


        senderMessages = MainActivity.messages.split(";", 0);

        handler.postDelayed(delayedRunnable, 3000);


        stopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Message.getInstance().getMessageList().clear();
            senderAdapter.notifyDataSetChanged();
            count = 0;
            handler.removeCallbacks(delayedRunnable);
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        handler.removeCallbacks(delayedRunnable);
        unregisterReceiver(myReceiver);
    }

    private int count = 0;

    public void sendMessage() {

        Log.d(TAG, "sendMessage: ");
        senderIntent.putExtra("sender message", senderMessages[count]);

        senderAdapter.insertItem(new MessageEntry(senderMessages[count], ""));

        sendBroadcast(senderIntent);

        scrollToCurrentPosition();
        count++;
    }

    public void scrollToCurrentPosition() {
        messageRecycler.scrollToPosition(Message.getInstance().getMessageList().size() - 1);

    }

    Runnable immediateRunnable = new Runnable() {
        @Override
        public void run() {
            if (count < senderMessages.length) {
                sendMessage();
            } else {
                handler.removeCallbacks(delayedRunnable);
            }

            Log.d(TAG, "run: immediate");
            handler.post(this);
        }
    };

    Runnable delayedRunnable = new Runnable() {
        @Override
        public void run() {

            if (count < senderMessages.length) {
                sendMessage();
            } else {
                handler.removeCallbacks(delayedRunnable);
            }

            if (!isMessageSendImmediately) {
                Log.d(TAG, "run: delayed");
                handler.postDelayed(this, 5000);
            } else {
                handler.post(this);
                isMessageSendImmediately = false;
            }
        }
    };
}
