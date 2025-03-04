package com.ch10;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView imageView1;
	private Context context;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }
    
    public void onClick(View view) {
    	new Upload().start();
    }
    
	private class Upload extends Thread {

		@Override
		public void run() {

			// 將圖形格式編碼字串格式
			String imageStr = imageToString(imageView1);

			ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("imageStr", imageStr));

			try {
				String user_agent = System.getProperty("http_agent");
				HttpClient httpclient = AndroidHttpClient.newInstance(user_agent);
				HttpPost httppost = new HttpPost(getResources()
								.getString(R.string.image_url_post));
				httppost.setEntity(new UrlEncodedFormEntity(pairs));
				HttpResponse response = httpclient.execute(httppost);
				final String status = response.getStatusLine().toString();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(context, "上傳成功 ! " + status,
								Toast.LENGTH_LONG).show();
					}
				});

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		// 圖形格式編碼字串格式
		private String imageToString(ImageView imageView) {
			BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView
					.getDrawable());
			Bitmap bitmap = bitmapDrawable.getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byte_arr = stream.toByteArray();
			return Base64.encodeToString(byte_arr, Base64.DEFAULT);

		}
	}
}
