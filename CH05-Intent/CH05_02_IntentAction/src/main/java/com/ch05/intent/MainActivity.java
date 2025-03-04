package com.ch05.intent;

import com.car.benz.Benz;
import com.car.bmw.Bmw;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.button1:
			intent.setClass(context, Benz.class);
			startActivity(intent);
			break;
		case R.id.button2:
			intent.setClass(context, Bmw.class);
			startActivity(intent);
			break;
		case R.id.button3:
			intent.setAction("good.car");
			intent = Intent.createChooser(intent, "請選擇一個App來執行!");
			startActivity(intent);
			break;
		}
	}
}
