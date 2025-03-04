package com.ch05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText editText1;
	private Context context;
	private String action;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		action = Intent.ACTION_CALL; // "android.intent.action.CALL"
		editText1 = (EditText) this.findViewById(R.id.editText1);
	}

	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.button1:
				String phoneNumber = editText1.getText().toString();
				if ((phoneNumber == null) || (phoneNumber.equals(""))) {
					Toast.makeText(context, R.string.message, 
							Toast.LENGTH_SHORT).show();
				} else {
					Uri uri = Uri.parse("tel:" + phoneNumber);
					Intent intent = new Intent(action, uri);
					startActivity(intent);
				}break;
		}
	}
}
