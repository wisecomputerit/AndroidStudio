package com.ch06;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
	}

	// Button click 事件回呼方法
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button1: // 1.雙鍵式對話框
			showDialog_1();
			break;
		case R.id.button2: // 2.多鍵式對話框
			showDialog_2();
			break;
		case R.id.button3: // 3.輸入型對話框
			showDialog_3();
			break;
		case R.id.button4: // 4.單選項型對話框
			showDialog_4();
			break;
		case R.id.button5: // 5.多選項型對話框
			showDialog_5();
			break;
		case R.id.button6: // 6.列表型對話框
			showDialog_6();
			break;
		case R.id.button7: // 7.自定佈局對話框
			showDialog_7();
			break;
		case R.id.button8: // 8.進度型對話框
			showDialog_8();
			break;
		}
	}

	// 1.雙鍵式對話框
	private void showDialog_1() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text1);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMessage(R.string.exit);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 2.多鍵式對話框
	private void showDialog_2() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text2);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMessage(R.string.like_android);
		builder.setPositiveButton(R.string.like, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(R.string.not, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNeutralButton(R.string.no_idea, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 3.輸入型對話框
	private void showDialog_3() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text3);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMessage(R.string.input_code);
		
		final EditText et = new EditText(context);
		builder.setView(et);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String message = getResources().getString(R.string.code);
				message += et.getText();
				Toast.makeText(context, message,
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 4.單選項型對話框
	private void showDialog_4() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text4);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		// 單選選項（注意：不可以與builder.setMessage()同時調用）
		builder.setSingleChoiceItems(R.array.drink, 0, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				List<String> list = 
						Arrays.asList((getResources().getStringArray(R.array.drink)));
				Toast.makeText(context, list.get(which), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
			
		});

		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 5.多選項對話框
	private void showDialog_5() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text5);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		final String[] drinkList = getResources().getStringArray(R.array.drink);
		final boolean[] checkList = new boolean[drinkList.length];
		// 多選選項（注意：不可以與builder.setMessage()同時調用）
		builder.setMultiChoiceItems(drinkList, checkList, 
				new OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked) {
				checkList[which] = isChecked;
			}
			
		});

		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<checkList.length;i++) {
					if(checkList[i]) {
						sb.append(drinkList[i] + ", ");
					}
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				
			}
		});
		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 6.列表型對話框
	private void showDialog_6() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text6);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		// 列表選項（注意：不可以與builder.setMessage()同時調用）
		builder.setItems(R.array.food, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				List<String> list = 
						Arrays.asList((getResources().getStringArray(R.array.food)));
				Toast.makeText(context, list.get(which), Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
			
		});

		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 7.自定佈局對話框
	private void showDialog_7() {
		// 自定Layout
		LayoutInflater inflater = getLayoutInflater();
		// 將 xml layout 轉換成視圖 View 物件
		View layout = inflater.inflate(R.layout.dialog, 
				(ViewGroup) findViewById(R.id.root));
		// 自定View
		Button randomBtn = (Button) layout.findViewById(R.id.randomBtn);
		final TextView randomText = 
				(TextView) layout.findViewById(R.id.randomText);
		randomBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取得1~100亂數
				int min = 1;
				int max = 100;
				Random r = new Random();
				int rNum = r.nextInt(max - min + 1) + min;
				randomText.setText(String.valueOf(rNum));
			}
			
		});
		
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.button_text7);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(layout);
		builder.setPositiveButton(R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String message = getResources().getString(R.string.lucky_num);
				message += "=" + randomText.getText();
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(R.string.cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 8.進度型對話框
	private void showDialog_8() {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setCancelable(true);
		dialog.setMessage(getResources().getString(R.string.button_text8));
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.show();
		new LoadImage(dialog).start();

	}
	
	// 載入影像
	private class LoadImage extends Thread {
		private ProgressDialog dialog;
		LoadImage(ProgressDialog dialog) {
			this.dialog = dialog;
		}
		@Override
		public void run() {
			try {
				String image_url = 
					getResources().getString(R.string.image_url);
				// 取得網路照片
				Bitmap bmp = new ImageUtils().getImage(image_url);
				// 將 Bitmap 轉 byte[]
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				// 將 byteArray 傳送至 ImageActivity
				Intent intent = new Intent(context, ImageActivity.class);
				intent.putExtra("image",byteArray);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 卸載所建立的 ProgressDialog 物件。
				dialog.dismiss();
			}

		}
	}
}
