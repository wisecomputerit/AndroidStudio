package com.ch17.startsevice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class ChronometerService extends Service {

    private Handler handler = new Handler();
    private Runnable showTime = new Runnable() {
        public void run() {
            // 顯示目前時間
            Log.i("mylog", new Date().toString());
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("mylog", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("mylog", "onStartCommand()");
        handler.post(showTime);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("mylog", "onDestroy()");
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }
}
