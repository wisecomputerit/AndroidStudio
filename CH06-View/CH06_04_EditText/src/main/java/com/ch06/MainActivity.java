package com.ch06;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	private AutoCompleteTextView act;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		act = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(context,
				R.array.languages, android.R.layout.simple_spinner_item);
		
		act.setThreshold(1); // 使用者只要輸入一個字就會動自動提示
		act.setAdapter(adapter);
	}
}
