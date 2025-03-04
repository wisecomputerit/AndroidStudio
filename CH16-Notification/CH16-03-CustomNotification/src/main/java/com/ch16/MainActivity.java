package com.ch16;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
	private NotificationManager notificationManager;
	private Context context;
	private RemoteViews remoteView;
	private Notification notification;
	private Notification.Builder builder;
	private int nid = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
	}

	public void onClick(View view) {
		createNotification(getBaseContext());
	}

	private void createNotification(Context context) {
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new Notification.Builder(context);
		builder.setSmallIcon(R.drawable.new_go_down).setTicker("下載中...")
				.setAutoCancel(false);
		notification = builder.build();
		// 建立遠程視圖（RemoteView）
		remoteView = new RemoteViews(this.getPackageName(),
				R.layout.notification);
		remoteView.setImageViewResource(R.id.image, R.drawable.new_go_down);
		remoteView.setTextViewText(R.id.text, "下載進度...");
		remoteView.setProgressBar(R.id.progressBar1, 100, 0, false);
		// 設定遠程視圖
		notification.contentView = remoteView;
		notificationManager.notify(nid, notification);
		// 啟動下載程序
		new Download().start();
	}

	private class Download extends Thread {
		private int currentProgress = 0;
		private int maxProgress = 100;
		public void run() {
			while (currentProgress < maxProgress) {
				currentProgress += 10;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						remoteView.setProgressBar(R.id.progressBar1, 100,
								currentProgress, false);
						remoteView.setTextViewText(R.id.text, "下載進度："
								+ currentProgress + "%");
						notificationManager.notify(nid, notification); // Update
					}
				});
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
			remoteView.setTextViewText(R.id.text, "下載完成 !");
			// 變更訊息嵌板樣式（增加：Action與ContentTitle）
			int requestCode = 1;
			Intent intent = new Intent(context, TrainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context,
					requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			builder.addAction(R.drawable.train, "請開啟", pendingIntent);
			builder.setContentTitle("下載完成 !");
			notification = builder.build();
			notificationManager.notify(nid, notification); // Update
		}
	}
}