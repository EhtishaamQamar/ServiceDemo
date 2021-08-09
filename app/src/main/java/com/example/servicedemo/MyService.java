package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;


public class MyService extends Service {

    private boolean isRandomGeneratorOn = false;
    private int randomNumber;


    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private final IBinder mBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("hello", "OnBinder Started");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("hello", "Service onStart has been Called on Thread :" + Thread.currentThread().getName());
        isRandomGeneratorOn=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateRandomNumber();
            }
        }).start();
        return START_STICKY;
    }

    private void generateRandomNumber() {

        while (isRandomGeneratorOn) {
            try {
                Thread.sleep(2000);
                randomNumber = new Random().nextInt(20);
                Log.i("hello", "Random No. Generated from Service :" + randomNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void stopRandomGenerator() {
        isRandomGeneratorOn = false;
    }

     int getRandomNumber() {
        return randomNumber;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomGenerator();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
