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
			createFile("textfile.txt");
			break;
		case R.id.button2:
			createCacheFile("cachefile.txt");
			break;
		}
	}

	// 建立私有文擋
	private void createFile(String fName) {
		try {
			// 建立應用程式私有文件
			FileOutputStream fOut = openFileOutput(fName, MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			// 寫入資料
			osw.write("She sell sea shells on the sea shore .");
			osw.close();
			Toast.makeText(context, "File saved successfully!",
					Toast.LENGTH_SHORT).show();
			// 讀取文擋資料
			readFile(new File(context.getFilesDir().getAbsolutePath(), 
						fName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 建立快取文擋
	private void createCacheFile(String fName) {
		try {
			// 取得快取目錄路徑
			File cacheDir = getCacheDir();
			File file = new File(cacheDir.getAbsolutePath(), fName);
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			// 寫入資料
			osw.write("Can you can a can as a canner can can a can ?");
			osw.flush();
			osw.close();
			Toast.makeText(context, "Cache File saved successfully!",
					Toast.LENGTH_SHORT).show();
			// 讀取文擋資料
			readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 讀取文擋資料
	private void readFile(File file) {
		char[] buffer = new char[1];
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();
        try {
            fr = new FileReader(file);
            while (fr.read(buffer)!= -1) {
                sb.append(new String(buffer));
            }
            textView1.setText(file.getAbsolutePath() + "\n\n" + 
            		sb.toString());
        }
        catch (IOException e) { }
        finally {
            try {
                fr.close(); // 關閉檔案
            }
            catch (IOException e) { }
        }
    }
}