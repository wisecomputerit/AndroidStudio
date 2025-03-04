package com.ch05;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText editText1;
	private String sdPath;
	private String fName = "android.png";
	private String action;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText1 = (EditText) findViewById(R.id.editText1);
		action = Intent.ACTION_SEND; // "android.intent.action.SEND"
		sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.button1:
				String shareText = editText1.getText().toString();
				Intent sendTextIntent = new Intent();
				sendTextIntent.setAction(action);
				sendTextIntent.putExtra(Intent.EXTRA_TEXT, shareText);
				sendTextIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendTextIntent, 
						getResources().getString(R.string.share)));
				break;
			case R.id.button2:
				File resFile = resToFile(R.drawable.android);
				Intent shareImageIntent = new Intent();
				shareImageIntent.setAction(action);
				shareImageIntent.putExtra(Intent.EXTRA_STREAM, 
						Uri.fromFile(resFile));
				shareImageIntent.setType("image/png");
				startActivity(Intent.createChooser(shareImageIntent, 
						getResources().getText(R.string.share)));
				break;
		}
	}
	// 將resource資源轉成檔案(file)
	private File resToFile(int resId) {
		InputStream in = getResources().openRawResource(resId);              
		try {
		    OutputStream out = new FileOutputStream(new File(sdPath, fName));  
		    byte[] buf = new byte[1024];
		    int len;
		    while ( (len = in.read(buf, 0, buf.length)) != -1) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		} catch (Exception e) { }
		return new File(sdPath + "/" + fName);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		// 刪除指定檔案
		new File(sdPath + "/" + fName).delete();
	}
}
