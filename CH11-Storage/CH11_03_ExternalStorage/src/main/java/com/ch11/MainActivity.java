package com.ch11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context context;
	private TextView textView1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		textView1 = (TextView) findViewById(R.id.textView1);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			createExternalFile("externalfile.txt");
			break;
		}
	}

	// 建立外部文擋
	private void createExternalFile(String fName) {
		// 判定是否可以針對外部儲存媒體進行讀寫
		if(!isExternalStorageAvailableAndWriteable()) {
			Toast.makeText(getBaseContext(), 
					"External storage can't access !",
					Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			// 取得外部儲存媒體路徑
			File extStorage = getExternalFilesDir(null);
			File file = new File(extStorage, fName);
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			// 寫入資料
			osw.write("I wish to wish the wish you wish to wish, "
					+ "but if you wish the wish the witch wishes, "
					+ "I won't wish the wish you wish to wish.");
			osw.flush();
			osw.close();
			Toast.makeText(getBaseContext(), 
					"File saved successfully!",
					Toast.LENGTH_SHORT).show();
			// 讀取文擋資料
			readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 判定是否可以針對外部儲存媒體進行讀寫
	private boolean isExternalStorageAvailableAndWriteable() {
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// 可讀寫 (read & write)
			externalStorageAvailable = externalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// 唯讀 (read-only)
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		} else {
			// 不可讀寫
			externalStorageAvailable = externalStorageWriteable = false;
		}
		return externalStorageAvailable && externalStorageWriteable;
	}

	// 讀取文擋資料
	private void readFile(File file) {
		char[] buffer = new char[1];
		FileReader fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new FileReader(file);
			while (fr.read(buffer) != -1) {
				sb.append(new String(buffer));
			}
			textView1.setText(file.getAbsolutePath() + "\n\n" + sb.toString());
		} catch (IOException e) {
		} finally {
			try {
				if(fr != null) {
					fr.close(); // 關閉檔案
				}
			} catch (IOException e) {
			}
		}
	}
}