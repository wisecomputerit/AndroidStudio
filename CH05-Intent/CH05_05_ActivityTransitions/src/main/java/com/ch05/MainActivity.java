package com.ch05;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void onClick(View v) {
		Intent intent = new Intent(this, PageActivity.class);
		intent.putExtra("title", v.getTag().toString());
		switch(v.getId()) {
			case R.id.imageButton1:
				intent.putExtra("resId", R.drawable.banner1);
				intent.putExtra("txtId", R.string.taiwan_info);
				break;
			case R.id.imageButton2:
				intent.putExtra("resId", R.drawable.banner2);
				intent.putExtra("txtId", R.string.sanya_info);
				break;
			case R.id.imageButton3:
				intent.putExtra("resId", R.drawable.banner3);
				intent.putExtra("txtId", R.string.chengde_info);
				break;
			case R.id.imageButton4:
				intent.putExtra("resId", R.drawable.banner4);
				intent.putExtra("txtId", R.string.great_wall_info);
				break;
			case R.id.imageButton5:
				intent.putExtra("resId", R.drawable.banner5);
				intent.putExtra("txtId", R.string.chong_cing_info);
				break;	
		}
		// Activity 轉場動畫
		startActivity(intent,  
	              ActivityOptions.makeSceneTransitionAnimation(
	            		  this, findViewById(v.getId()), v.getTag().toString())
	            		  .toBundle());
	}
}
