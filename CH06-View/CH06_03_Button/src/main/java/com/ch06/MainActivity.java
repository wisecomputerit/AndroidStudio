package com.ch06;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.QuickContactBadge;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	private ToggleButton toggleButton1;
	private QuickContactBadge email_badge, phone_badge;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton1); 
		toggleButton1.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Toast.makeText(context, "狀態：" + isChecked, 
						Toast.LENGTH_SHORT).show();
			}
			
		});
		
		// QuickContactBadge:email 
		email_badge = (QuickContactBadge) findViewById(R.id.email_badge); 
        email_badge.assignContactFromEmail("demo@gmail.com", true); 
		
        // QuickContactBadge:phone
        phone_badge = (QuickContactBadge) findViewById(R.id.phone_badge); 
        phone_badge.assignContactFromPhone("+886227735243", true);    
        
	}
	
	// Button click 事件回呼方法
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.button1:
				Toast.makeText(context, R.string.button_text, 
						Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
