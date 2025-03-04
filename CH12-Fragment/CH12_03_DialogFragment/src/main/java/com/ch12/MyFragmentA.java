package com.ch12;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentA extends Fragment {
	
	private TextView textView1;
	private ImageView imageView1;
	private Context context;
	
	// 回呼狀態 : Fragment 剛被建立
	// 使用時機 : 用來設定物件變數初始值
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		
	}

	// 回呼狀態 : Fragment 即將可以顯示在螢幕時
	// 使用時機 : 用來設定 Fragment Layout 界面佈局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_a, container, false);
		
		// 記得要 return Fragment Layout 物件
		return view;
	}

	// 回呼狀態 : 可以開始取得 Fragment Layout 界面物件
	// 使用時機 : 用來設定 Layout 上的 UI View 物件
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// 透過 getView() 取得 Fragment 在 onCreateView() 方法中所定義的 Layout
		textView1 = (TextView)getView().findViewById(R.id.textView1); 
		textView1.setText("X'max Tree");
		imageView1 = (ImageView)getView().findViewById(R.id.imageView1); 
		imageView1.setImageResource(R.drawable.tree);
		
		// 設定 DialogFragment
		imageView1.setClickable(true);
		imageView1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 建立 MyDialogFragment
				MyDialogFragment dialogfragment = new MyDialogFragment();
				// 建立 Bundle
				Bundle args = new Bundle();
		        args.putString("title", "我是聖誕樹");
		        dialogfragment.setArguments(args);
				// 顯示 MyDialogFragment
		        dialogfragment.show(getFragmentManager(), "dialog");
			}
		});
	}
	
	// 回呼狀態 : Fragment 從螢幕上消失時
	// 使用時機 : 用來設定/儲存暫存資料
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
}
