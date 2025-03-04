package com.ch05;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class PageActivity extends Activity {
	private TextView textView1;
	private ImageView imageView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_main);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		
		String title = getIntent().getStringExtra("title");
		int resId = getIntent().getIntExtra("resId", 0);
		int txtId = getIntent().getIntExtra("txtId", 0);
		String txt = getResources().getString(txtId);
		showData(title, resId, txt);
	}
	
	private void showData(String title, int resId, String txt) {
		setTitle(title);
		String html = txt.replaceAll(title, 
				"<u><font color='yellow'>" + title + "</font></u>");
		textView1.setText(Html.fromHtml(html));
		imageView1.setImageResource(resId);
	}
}
