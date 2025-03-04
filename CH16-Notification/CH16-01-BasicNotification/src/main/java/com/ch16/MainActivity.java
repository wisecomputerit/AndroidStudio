package com.ch16;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {
	private NotificationManager notificationManager;
	private Context context;
	private CheckBox checkBox1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;

		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		// 1.建立-通知服務管理器
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.notification:
			createNotification(getBaseContext(), 0);
			break;
		case R.id.cancel:
			//notificationManager.cancel(0); // 消除 nid 的 Notification
			notificationManager.cancelAll(); // 消除全部 Notification
			Toast.makeText(context, "Cancel Notification !", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}
	private void createNotification(Context context, int nid) {
		// 2.建立-通知服務建構器
		Notification.Builder builder = new Notification.Builder(context);
		// 3.建立按下訊息嵌板後所要轉跳的Intent
		Intent intent = new Intent(context, TrainActivity.class);
		// 設定Intent標誌參數
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
				Intent.FLAG_ACTIVITY_SINGLE_TOP | 
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// 設定請求碼
		int requestCode = 1;
		PendingIntent pendingIntent = PendingIntent.getActivity(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// 4.設定震動頻率
		long[] vibratepattern = { 100, 400, 500, 400 };
		// Respurces 轉 bitmap
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), 
				R.drawable.train);
		// 5.定義 Notification.Builder 建構器
		builder.setSmallIcon(R.drawable.information) // 通知服務 icon
				.setLargeIcon(bmp)
				.setContentTitle("標題:火車來了, n_id=" + nid) // 標題
				.setContentText("普悠瑪號") // 內文
				.setContentInfo("2013/2上路") // 信息
				.setTicker("停看聽！") // Ticker 標題
				.setLights(0xFFFFFFFF, 1000, 1000) // LED
				.setVibrate(vibratepattern) // 震動
				.setContentIntent(pendingIntent) // 設定Intent服務
				.setAutoCancel(true); // true：按下訊息嵌板後會自動消失
		/*
		在鎖屏上揭露完整訊息：Notification.VISIBILITY_PUBLIC
		基本的資訊與通知的圖示：Notification.VISIBILITY_PRIVATE（預設）
		在鎖屏上揭露訊息：Notification.VISIBILITY_SECRET
		*/
		builder.setVisibility(Notification.VISIBILITY_PRIVATE);
		// 抬頭顯示儀
		if(checkBox1.isChecked()) {
			builder.setPriority(Notification.PRIORITY_HIGH); // 亦可帶入Notification.PRIORITY_MAX參數
		}
		Notification notification = builder.build();
		notificationManager.notify(nid, notification); // 發佈Notification
	}
}