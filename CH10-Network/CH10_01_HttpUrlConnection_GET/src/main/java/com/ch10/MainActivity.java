package com.ch10;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView imageView1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }
    
    public void onClick(View view) {
    	imageView1.setImageBitmap(null);
    	new LoadImage().start();
    }
    
    private class LoadImage extends Thread {
    	private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				double sum = (Double)msg.obj; // 取得 msg 所帶入的 object 參數
				setTitle("Progress：" + (int)sum + " %");
			}
    	};
    	
		private Bitmap bitmap = null;
		private InputStream inputStream = null;
		private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		public void run() {
			try {
				String image_url = getResources()
						.getString(R.string.image_url_get);
				URL url = new URL(image_url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				inputStream = conn.getInputStream();
				double fullSize = conn.getContentLength(); // 總長度
				byte[] buffer = new byte[64]; // buffer (每次讀取長度)
				int readSize = 0; // 當下讀取長度
				
				double sum = 0;
				while ((readSize = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, readSize);
					sum += (readSize / fullSize) * 100; // 累計讀取進度
					Message message = handler.obtainMessage(1, sum);
					handler.sendMessage(message);
				}
				
				// 將 outputStream 轉 byte[] 再轉 Bitmap
				byte[] result = outputStream.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
				
				// 資料處理完畢修改 View
				handler.post(new Runnable() {
					public void run() {
						// 將 Bitmap 注入 ImageView
						imageView1.setImageBitmap(bitmap);
						imageView1.setVisibility(View.VISIBLE);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					outputStream.close();
				} catch(Exception e) {
					
				}
				setTitle("完成");
			}
		}
	}
}
