package com.example.sender2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView scriptText;
    String[] senderMessages;


    static String messages = "Hello, it's me;" +
            "I was wondering if after all these years you'd like to meet;" +
            "To go over everything;" +
            "They say that time's supposed to heal ya;" +
            "But I ain't done much healing;" +
            "Hello, can you hear me?;" +
            "I'm in California dreaming about who we used to be;" +
            "When we were younger and free;" +
            "I've forgotten how it felt before the world fell at our feet;" +
            "There's such a difference between us;" +
            "And a million miles;";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        scriptText = findViewById(R.id.scriptText);
        TextView scriptTitle = findViewById(R.id.scriptTitle);
        TextView senderTitle = findViewById(R.id.senderTitle);

        displaySenderMessages();

        startButton.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), SenderActivity.class);
            startActivity(intent);
        });

    }
    public void displaySenderMessages(){
        senderMessages = split(messages);
        String printText = "";
        for (String s: senderMessages){
            printText += s + ";\n";
        }
        scriptText.setText(printText);
    }

    public String[] split (String messages){
        return messages.split(";", 0);
    }

    void onoiuoio(){
        Intent service = new Intent(this, ServiceClass.class);
        startService(service);
    }
}