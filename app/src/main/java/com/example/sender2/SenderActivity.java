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

    BroadcastReceiver myReceiver = new MyReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("receiver message") != null) {
                Log.d(TAG, "onReceive: " + intent.getStringExtra("receiver message"));

                checkAndRemoveWaitingResponse();

                // remove the old runnable when new message received
                // because a sender message has to send immediately
                // which breaking the cycle of old runnable
                // the new runnable begin after the sender message sent
                handler.removeCallbacks(messageRunnable);
                handler.removeCallbacks(WaitingResponseRunnable);

                senderAdapter.insertItem(new MessageEntry("", intent.getStringExtra("receiver message"), false));
                if (count < senderMessages.length) {
                    sendMessage();
                    handler.postDelayed(WaitingResponseRunnable, 1000);
                    handler.postDelayed(messageRunnable, 5000);
                }
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


        senderIntent = new Intent("com.example.myMessage");
        senderIntent.setComponent(new ComponentName("com.example.receiver2", "com.example.receiver2.MyReceiver"));

        IntentFilter intentFilter = new IntentFilter("update message");
        registerReceiver(myReceiver, intentFilter);


        senderAdapter = new SenderAdapter(Message.getInstance().getMessageList());

        messageRecycler.setAdapter(senderAdapter);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));


        senderMessages = MainActivity.messages.split(";", 0);

        handler.postDelayed(messageRunnable, 3000);

        stopButton.setOnClickListener(v -> {
            this.finish();
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Message.getInstance().getMessageList().clear();
        senderAdapter.notifyDataSetChanged();
        count = 0;

        handler.removeCallbacks(WaitingResponseRunnable);
        handler.removeCallbacks(messageRunnable);
        unregisterReceiver(myReceiver);

        Log.d(TAG, "onDestroy: ");
    }


    private int count = 0;

    public void sendMessage() {
        senderIntent.putExtra("sender message", senderMessages[count]);

        senderAdapter.insertItem(new MessageEntry(senderMessages[count], "", false));

        sendBroadcast(senderIntent);

        scrollToCurrentPosition();
        count++;
    }

    public void scrollToCurrentPosition() {
        messageRecycler.scrollToPosition(Message.getInstance().getMessageList().size() - 1);

    }

    public void checkAndRemoveWaitingResponse() {

        if (count > 0 && Message.getInstance().getLastMessage().getIsWaitingResponse()) {
            Log.d(TAG, "checkAndRemoveWaitingResponse: " + Message.getInstance().getLastMessage().getIsWaitingResponse());

            Message.getInstance().getMessageList().remove(Message.getInstance().getLastMessage());
            senderAdapter.notifyItemRemoved(Message.getInstance().getMessageList().size());
            scrollToCurrentPosition();
        }

    }

    public void displayWaitingResponse() {
        senderAdapter.insertItem(new MessageEntry("", "", true));
        scrollToCurrentPosition();
    }

    Runnable WaitingResponseRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: displayWaitingResponse");
            displayWaitingResponse();
        }
    };

    Runnable messageRunnable = new Runnable() {
        @Override
        public void run() {
            // after 5s, remove the waiting response message
            checkAndRemoveWaitingResponse();

            // if sender message list end, stop all runnable
            if (count == senderMessages.length) {
                finish();
            } else if (count < senderMessages.length) {
                sendMessage();
                handler.postDelayed(WaitingResponseRunnable, 1000);
                handler.postDelayed(this, 5000);
            }
        }
    };
}
