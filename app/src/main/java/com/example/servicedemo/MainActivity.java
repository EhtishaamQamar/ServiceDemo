package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("hello","Activity oncreate");
        intent = new Intent(this , MyService.class);
    }

    public void startService(View view) {
        startService(intent);
    }

    public void stopService(View view) {
        stopService(intent);
    }
}