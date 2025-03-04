package com.pcschool.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	private EditText editText1, editText2;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);


	}

	public void onClick(View view) {

		String urlString = "https://ditu.google.com/maps?f=d&dirflg=%s&saddr=%s&daddr=%s&hl=zh_TW&t=m";
		String dirflg = "";

		// 取得 LatLng 物件
		LatLng saddrLatLng = GeoUtil.getLatLngByAddress(editText1.getText().toString());
		LatLng daddrLatLng = GeoUtil.getLatLngByAddress(editText2.getText().toString());

		// 組合緯經度參數
		String saddr = saddrLatLng.latitude + "," + saddrLatLng.longitude;
		String daddr = daddrLatLng.latitude + "," + daddrLatLng.longitude;

		switch(view.getId()) {
			case R.id.imageButton1:
				dirflg = "d";
				break;
			case R.id.imageButton2:
				dirflg = "r";
				break;
			case R.id.imageButton3:
				dirflg = "w";
				break;
		}
		urlString = urlString.format(urlString, dirflg, saddr, daddr);

		Uri uri = Uri.parse(urlString);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);

	}


}


