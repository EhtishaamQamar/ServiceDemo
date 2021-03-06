package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private ServiceConnection serviceConnection;
    private MyService myService;
    private boolean isServiceConnected;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("hello","Activity onCreate");
        intent = new Intent(this , MyService.class);
        textView = findViewById(R.id.textView);

        Intent mapIntent = new Intent(Intent.ACTION_SEND);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent,
                PackageManager.MATCH_ALL);
        boolean isIntentSafe = activities.size() > 0;

Log.d("hello", String.valueOf(isIntentSafe));
for(ResolveInfo x : activities) {
    Log.d("hello",x.toString());
}

    }

    public void startService(View view) {
        startService(intent);
    }

    public void stopService(View view) {
        stopService(intent);
    }

    public void bindService(View view) {

        if(serviceConnection == null){
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) iBinder;
                myService = myServiceBinder.getService();
                isServiceConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isServiceConnected = false;

            }
        };
        }
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }



    public void unBindService(View view) {
        if(isServiceConnected){
            unbindService(serviceConnection);
            isServiceConnected=false;
        }
    }

    public void retrieveRandomNumber(View view) {
        if(isServiceConnected) {
            textView.setText(getString(R.string.random_number,myService.getRandomNumber()));
        } else
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
    }
}