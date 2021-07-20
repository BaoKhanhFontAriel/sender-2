package com.example.sender2;

import android.content.BroadcastReceiver;
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
    RecyclerView messageRecycler;
    SenderAdapter senderAdapter;
    Intent senderIntent;
    String[] senderMessages;

    Handler handler = new Handler(Looper.getMainLooper());

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("")) {
                String message = intent.getStringExtra("message");
                Message.getInstance().addMessage(new MessageEntry("", message));
                senderAdapter.insertItem(new MessageEntry("", message));
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

        IntentFilter intentFilter = new IntentFilter("com.example.myMessage");
        registerReceiver(broadcastReceiver, intentFilter);

        // Send the message
        senderIntent = new Intent();
        senderIntent.setAction("com.example.myMessage");

        senderMessages = MainActivity.messages.split(";", 0);


        handler.post(runnable);


        stopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Message.getInstance().getMessageList().clear();
            senderAdapter.notifyDataSetChanged();
        });

    }

    private int count = 0;

    public void sendMessage() {
        Message.getInstance().addMessage(new MessageEntry(senderMessages[count], ""));
        senderIntent.putExtra("message", senderMessages[count]);
        sendBroadcast(senderIntent);
        senderAdapter.notifyItemInserted(count);
        messageRecycler.scrollToPosition(count);
        count++;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendMessage();
            if (count < senderMessages.length) {
                if (Message.getInstance().getMessageList().isEmpty()) {
                    Log.d("TAG", "run: work ");
                    handler.postDelayed(this, 3000);
                } else handler.postDelayed(this, 5000);
            }
        }
    };

}
