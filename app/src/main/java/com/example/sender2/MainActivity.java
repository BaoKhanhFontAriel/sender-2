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


    static String messages = "Quo modi sit;" +
            " reprehenderit;" +
            " natus cumque a odio; " +
            "Explicabo voluptatem;" +
            " et in culpa;" +
            " incidunt suscipit;" +
            " odio neque; " +
            "In aut repellendus;" +
            " eveniet voluptas hic et;" +
            " Deserunt libero ut sed; " +
            "Aut ut ut corporis veritatis;" +
            " voluptate pariatur et illo;" +
            "Odit aut non quis;" +
            " molestiae praesentium;";


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