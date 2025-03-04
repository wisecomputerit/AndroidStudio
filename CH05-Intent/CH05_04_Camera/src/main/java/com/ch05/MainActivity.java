package com.ch05;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context context;
	private File file;
	private String action;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		action = MediaStore.ACTION_IMAGE_CAPTURE; 
		// 取得SD卡根路徑
		String sdPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		// 將照片存入SD卡根路徑，檔名：photo1.png
		file = new File(sdPath + "/" + "photo1.png");
	}
	// 拍照完畢回呼函式
	protected void onActivityResult(int requestCode, int resultCode, 
			Intent data) {
		if (requestCode == 100) {
			// 取得相片 URI 路徑
			Uri imgUri = Uri.parse(file.getAbsolutePath());
			// 置入ImageView視圖中
			ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
			imageView1.setImageURI(imgUri);
			Toast.makeText(context, R.string.finish, Toast.LENGTH_SHORT).show();
		}
	}
	// onClick 監聽器
	public void onClick(View view) {
		// Intent 相機
		Intent it = new Intent(action);
		// 輸出參數：相機拍照後存入指定路徑
		it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		// 回調型 intent
		startActivityForResult(it, 100);
	}
}
