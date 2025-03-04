package com.ch06;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {
	
	private VideoView videoView1;
	private MediaController mediaController1;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		// 建立 VideoView
		videoView1 = (VideoView) findViewById(R.id.videoView1);
		// 建立 MediaController
		mediaController1 = new MediaController(context);
		
		// 本地專案媒體路徑
		String localpath = "android.resource://" + 
					getPackageName() + "/" + R.raw.lego;
		// 網路媒體路徑
		String webpath = getResources().getString(R.string.rstp);
		
		// 建立 uri
		Uri uri = Uri.parse(localpath);System.out.println(uri);
		// 設置媒體 uri
		videoView1.setVideoURI(uri);
		// 設定媒體控制板面
		videoView1.setMediaController(mediaController1); 
		
	}
}
