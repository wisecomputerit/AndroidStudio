package com.ch10;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class VolleyActivity extends Activity {

	private Context context; // 宣告 Context 場景物件
	private Button button1; // 宣告 Button
	private ProgressDialog progressDialog; // 宣告 ProgressDialog
	private ImageView imageView1; // 宣告 ImageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;

		// 建立 Button 與 ImageView 的實體
		button1 = (Button) findViewById(R.id.button1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);

	}

	// Button 監聽器
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.button1:
				imageView1.setVisibility(View.INVISIBLE);
				
				RequestQueue mQueue = Volley.newRequestQueue(context); 
				ImageRequest imageRequest = new ImageRequest(
						getResources().getString(R.string.image_url),
						new Response.Listener<Bitmap>() {
							@Override
							public void onResponse(Bitmap response) {
								imageView1.setVisibility(View.VISIBLE);
								imageView1.setImageBitmap(response);
							}
						}, 
						0, 
						0, 
						Config.RGB_565, 
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								imageView1.setImageResource(R.drawable.android_icon);
							}
						});
				mQueue.add(imageRequest);
				
				break;
		}

	}

	
}
