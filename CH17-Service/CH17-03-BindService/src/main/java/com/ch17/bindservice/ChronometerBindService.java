package com.ch17.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class ChronometerBindService extends Service {
    private final IBinder myBinder = new MyBinder();
    private Handler handler = new Handler();
    private Runnable showTime = new Runnable() {
        public void run() {
         // 顯示目前時間
        Log.i("mylog", new Date().toString());
        handler.postDelayed(this, 1000);
        }
    };

    // 實作 Binder
    public class MyBinder extends Binder {
        public ChronometerBindService getService() {
            return ChronometerBindService.this;
        }
    }

    public void run() {
        handler.post(showTime);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("mylog", "onBind()");

        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("mylog", "onRebind()");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("mylog", "onUnbind()");
        handler.removeCallbacks(showTime);
        return super.onUnbind(intent);
    }
}
