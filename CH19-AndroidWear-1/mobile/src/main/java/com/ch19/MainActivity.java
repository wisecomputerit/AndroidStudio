package com.ch19;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }

    public void onClick(View view) {
        createNotification();
    }

    private void createNotification() {

        NotificationManager notificationManager=
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        Notification.Builder builder = new Notification.Builder(context);

        long[] vibratepattern = { 100, 400, 500, 400 };

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.kweather);

        Uri uri = Uri.parse("https://tw.news.yahoo.com/weather-forecast/");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
        PendingIntent pWebIntent = PendingIntent.getActivity(context, 1,
                webIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Action action = new Notification.Action.Builder(
                android.R.drawable.ic_menu_share, "一週天氣", pWebIntent).build();

        builder.setSmallIcon(R.drawable.partly_cloudy) // 通知服務 icon
                .setLargeIcon(largeIcon)
                .setContentTitle("今日天氣") // 標題
                .setContentText("多雲時晴，偶有強風！") // 內文
                .setContentInfo("13度 C") // 信息
                .setTicker("天氣") // Ticker 標題
                .setLights(0xFFFFFFFF, 1000, 1000) // LED
                .setVibrate(vibratepattern) // 震動
                .addAction(action)
                .setAutoCancel(true);

        notification = builder.build();
        notificationManager.notify(100, notification);
    }
}
