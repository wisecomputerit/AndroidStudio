package com.ch12;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		/*
		 * inflater的inflate方法的三個參數分別是：
		 * int resource：fragment的layout資源ID。
		 * ViewGroup root：存放fragment的layout的ViewGroup
		 * boolean attachToRoot：指定展開的佈局是否依附到ViewGroup中
	 	*/
		View view = inflater.inflate(R.layout.fragment_a, container, false);
		
		// 記得要 return Fragment Layout 物件
		return view;
	}

	// 回呼狀態 : 可以開始取得 Fragment Layout 界面物件
	// 使用時機 : 用來設定 Layout 上的 UI View 物件
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// 透過 getView() 取得Fragment的Layout實例
		textView1 = (TextView)getView().findViewById(R.id.textView1); 
		textView1.setText("X'mas Tree");
		
		imageView1 = (ImageView)getView().findViewById(R.id.imageView1); 
		imageView1.setImageResource(R.drawable.tree);
	}
	
}
