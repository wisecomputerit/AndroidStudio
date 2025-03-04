package com.ch06;

import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean play; // 記錄 play/pause 狀態
	private ImageButton fab;
	private TextView textView1;
	private String article;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fab = (ImageButton) findViewById(R.id.fab);
		textView1 = (TextView) findViewById(R.id.textView1);
		article = getResources().getString(R.string.gueishan_island);
		// 實作視圖外觀
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {
				// 設定視圖尺寸
				int size = getResources().getDimensionPixelSize(
						R.dimen.fab_size);
				// 將視圖外觀設成橢圓形（本例是設成圓形）
				outline.setOval(0, 0, size, size);
			}
		};
		// 設定 fab（ImageButton）外框樣式
		fab.setOutlineProvider(viewOutlineProvider);
		// 註冊 fab onclick 事件
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 變更 fab 圖樣
				fab.setImageResource(play?R.drawable.play:R.drawable.pause);
				// 改變 play/pause 狀態
				play =! play;
				// 啟動文字跑馬燈
				new Marquee().start();
			}
		});
	}
	// 自製文字跑馬燈
	private class Marquee extends Thread {
		int length = 0;
		int maxlength = article.length();
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String msgString = (String)msg.obj; // 取得 Message.obj 參數
				textView1.setText(msgString);
			}
		};
		public void run() {
			if(length <= maxlength && play) {
				play();
			}
		}
		private void play() {
			length++;
			// 送出訊息
			if(length <= maxlength && play) {
				String obj = (String) article.subSequence(0, length);
				Message message = handler.obtainMessage(1, obj);
				handler.sendMessageDelayed(message, 100);
				play();
			}
		}
	}
}
