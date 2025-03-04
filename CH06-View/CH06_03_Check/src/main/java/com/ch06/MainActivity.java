package com.ch06;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
	private CheckBox checkBox1;
	private CheckedTextView checkedTv1;
	private RadioGroup radioGroup1;
	private Switch switch1;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkedTv1 = (CheckedTextView) findViewById(R.id.checkedTv1);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		switch1 = (Switch) findViewById(R.id.switch1);
		
		checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Toast.makeText(context, String.valueOf(isChecked), 
						Toast.LENGTH_SHORT).show();
			}
		});
		
		checkedTv1.setClickable(true);
		checkedTv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckedTextView ctv = (CheckedTextView)v;
				ctv.setChecked(!ctv.isChecked());
			}
		});
		
		radioGroup1.setOnCheckedChangeListener(
				new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch(checkedId) {
					case R.id.radio1:
						Toast.makeText(context, R.string.radio_man, 
								Toast.LENGTH_SHORT).show();
						break;
					case R.id.radio2:
						Toast.makeText(context, R.string.radio_woman, 
								Toast.LENGTH_SHORT).show();
						break; 	
				}
			}
		});
		
		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Toast.makeText(context, buttonView.getText() + ":" + isChecked, 
						Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
