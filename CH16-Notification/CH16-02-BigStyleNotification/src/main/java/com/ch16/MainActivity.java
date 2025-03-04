package com.ch16;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Style;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private NotificationManager notificationManager;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		// 1.建立-通知服務管理器
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bigText:
			createNotification(getBaseContext(), 1, getBigTextStyle());
			break;
		case R.id.bigPicture:
			createNotification(getBaseContext(), 2, getBigPictureStyle());
			break;
		case R.id.inboxStyle:
			createNotification(getBaseContext(), 3, getInboxStyle());
			break;
		case R.id.cancel:
			notificationManager.cancelAll(); // 消除全部 Notification
			Toast.makeText(context, "Cancel Notification !", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	// 大文字呈現風格
	private Style getBigTextStyle() {
		// 建立大文字呈現風格實例
		Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
		bigTextStyle.bigText(getResources().getString(R.string.info));
		// 設定總結文字
		bigTextStyle.setSummaryText("台鐵最新列車");
		return bigTextStyle;
	}

	// 大圖呈現風格
	private Style getBigPictureStyle() {
		// 建立大圖呈現風格實例
		Notification.BigPictureStyle bigPicStyle = new Notification.BigPictureStyle();
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.train1);
		bigPicStyle.bigPicture(bm);
		bigPicStyle.setSummaryText("台鐵最新列車圖");
		return bigPicStyle;
	}
	// 收件匣風格-多項列呈現風格
	private Style getInboxStyle() {
		// 建立收件匣呈現風格實例
		Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
		String[] events = new String[3];
		events[0] = "推拉式電車-E1000（孫悟空）";
		events[1] = "電聯車-EMU100、EMU200、EMU300";
		events[2] = "傾斜式列車-TEMU1000（太魯閣）、TEMU2000（普悠瑪）";
		// 將項目列表注入收件箱風格
		for (int i = 0; i < events.length; i++) {
			// 在收件匣風格中加入項目
			inboxStyle.addLine(events[i]);
		}
		inboxStyle.setSummaryText("台鐵自強號種類");
		return inboxStyle;
	}

	private void createNotification(Context context, int nid, Style style) {
		// 2.建立-通知服務建構器
		Notification.Builder builder = new Notification.Builder(context);

		// 3.建立所要轉跳的Activity
		Intent intent = new Intent(context, TrainActivity.class);
		// Intent 的旗標常數參數設定
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		int requestCode = 1;
		PendingIntent pendingIntent = PendingIntent.getActivity(context,
				requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 4.定義 Notification.Builder 建構器
		builder.setSmallIcon(R.drawable.information) // 通知服務 icon
				.setContentTitle("標題:火車來了, n_id=" + nid) // 標題
				.setContentText("自強號") // 內文
				.setContentInfo("目前行駛中...") // 信息
				.setTicker("停看聽 !") // Ticker 標題
				.setContentIntent(pendingIntent) // 設定Intent服務
				.setAutoCancel(false);
		// 設定訊息嵌板風格
		builder.setStyle(style);
		Notification notification = builder.build();
		notificationManager.notify(nid, notification); // 註冊 Notification
	}
}