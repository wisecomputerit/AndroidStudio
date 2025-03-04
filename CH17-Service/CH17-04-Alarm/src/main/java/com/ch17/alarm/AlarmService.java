package com.ch17.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import util.libs.stock.Stock;
import util.libs.stock.StockService;

public class AlarmService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String stockNo = intent.getExtras().getString("stockNo");

        // 抓取報價
        new Thread() {
            @Override
            public void run() {
                StockService stockService = new StockService();
                Stock stock = stockService.getStock(stockNo);

                // 建立 Notification
                int nid = 1;
                NotificationManager notificationManager =
                        (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder =
                        new Notification.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.stock_ticker) // 通知服務 icon
                        .setContentTitle("台積電股價通知") // 標題
                        .setContentText("成交：" + stock.get成交() + " " + stock.get漲跌()) // 內文
                        .setContentInfo("昨收：" + stock.get昨收()) // 信息
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);

                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                // 抬頭顯示儀
                builder.setPriority(Notification.PRIORITY_HIGH); // 亦可帶入Notification.PRIORITY_MAX參數
                Notification notification = builder.build();
                notificationManager.notify(nid, notification); // 發佈Notification
            }

        }.start();

        // 關閉服務
        stopSelf();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.i("mylog", "onDestroy()");
        super.onDestroy();
    }
}
