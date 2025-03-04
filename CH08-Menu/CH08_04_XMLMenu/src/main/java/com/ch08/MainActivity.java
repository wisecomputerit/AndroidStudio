package com.ch08;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	private RelativeLayout layout1;
	private ImageView imageView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		registerForContextMenu(layout1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//MenuInflater的作用可將Menu XML檔案實體轉換成java Menu的物件形態
		MenuInflater inflater = getMenuInflater();
		//根據 menu xml 建出 menu 實體物件結構
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		actionByMenuItem(item);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, 
								ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		actionByMenuItem(item);
		return true;	
	}
	
	private void actionByMenuItem(MenuItem item) {
		Toast.makeText(context, "按下了" + item.getTitle(), Toast.LENGTH_SHORT).show();
		switch(item.getItemId()) {
			case R.id.mario:
				imageView1.setImageResource(R.drawable.mario);
				break;
			case R.id.sonic:
				imageView1.setImageResource(R.drawable.sonic);
				break;
			case R.id.quit:
				finish();
				break;
		}
	}
	
}