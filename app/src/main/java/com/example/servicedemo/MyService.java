package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;


public class MyService extends Service {

    private boolean isRandomGeneratorOn = false;
    private Random random;
    private int randomNumber;



    class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder mBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("hello","OnBinder Started");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("hello","Service onStart has been Called on Thread :"+Thread.currentThread().getName());

       new Thread(new Runnable() {
           @Override
           public void run() {
               generateRandomNumber();
           }
       });
        return super.onStartCommand(intent, flags, startId);
    }

    private void generateRandomNumber() {
        random = new Random();
        while(isRandomGeneratorOn) {
            try {
                Thread.sleep(2000);
                randomNumber=random.nextInt(20);
                Log.i("hello","Random No. Generated from Service :"+randomNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void stopRandomGenerator() {
        isRandomGeneratorOn=false;
    }

    private int getRandomNumber() {
        return randomNumber;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
