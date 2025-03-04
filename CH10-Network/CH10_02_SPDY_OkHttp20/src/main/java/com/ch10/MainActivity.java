package com.ch10;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private OkHttpClient client;
	private String[] imageUrls;
	private LinearLayout imageLayout;
	private Context context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        
        imageUrls = getResources().getStringArray(R.array.image_urls_get);
        imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        client = new OkHttpClient();
    }
    
    public void onClick(View view) {
    	imageLayout.removeAllViews();
    	for(String url : imageUrls) {
    		ImageView imageView = new ImageView(context);
    		ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
    		progressBar.setMax(100);
    		
    		imageLayout.addView(imageView);
    		imageLayout.addView(progressBar);
    		
    		new LoadImage(url, imageView, progressBar).start();
    	}
    }
    
    private class LoadImage extends Thread {
		private String image_url;
		private ImageView imageView;
		private ProgressBar progressBar;
		
    	private Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				double sum = (Double)msg.obj; // 取得 msg 所帶入的 object 參數
				progressBar.setProgress((int)sum);
			}
    		
    	};
		private Bitmap bitmap = null;
		private InputStream inputStream = null;
		private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		public LoadImage(String image_url, ImageView imageView, ProgressBar progressBar) {
			this.image_url = image_url;
			this.imageView = imageView;
			this.progressBar = progressBar;
		}
		
		public void run() {
			try {
				URL url = new URL(image_url);
				
				// 替換成 OkHttp 2.0 ---------------------------------------
				Request request = new Request.Builder().url(url).build();
				Response response = client.newCall(request).execute();
				inputStream = response.body().byteStream();
				double fullSize = response.body().contentLength(); // 總長度
				//---------------------------------------------------------
				
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
						imageView.setImageBitmap(bitmap);
						imageView.setAdjustViewBounds(true);
						imageLayout.removeView(progressBar);
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
				//setTitle("完成");
			}
		}

	}
    
}
