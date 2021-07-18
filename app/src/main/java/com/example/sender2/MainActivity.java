package com.example.sender2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("")){
                String dta = intent.getStringExtra("mess");
                list.ad
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IntentFilter intentFilter = new IntentFilter(new Intent("jhgjhgjh"));


        registerReceiver(broadcastReceiver, intentFilter);


        Intent i = new Intent();
        i.setAction("send message");
        i.putExtra("mess", "ljhbjlh ");
        sendBroadcast(i);

        handler.post(runnable);

    }



    void onoiuoio(){
        Intent service = new Intent(this, ServiceClass.class);
        startService(service);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            /////

            handler.postDelayed(this, 3000);

        }
    };


}