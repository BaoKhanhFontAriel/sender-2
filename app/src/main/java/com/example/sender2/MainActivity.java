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


    static String messages = "1. Hello, it's me;" +
            "2. I was wondering if after all these years you'd like to meet;" +
            "3. To go over everything;" +
            "4. They say that time's supposed to heal ya;" +
            "5. But I ain't done much healing;" +
            "6. Hello, can you hear me?;" +
            "7. I'm in California dreaming about who we used to be;" +
            "8. When we were younger and free;" +
            "9. I've forgotten how it felt before the world fell at our feet;" +
            "10. There's such a difference between us;" +
            "11. And a million miles;";


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

            messages = scriptText.getText().toString().replace("\n", "");
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