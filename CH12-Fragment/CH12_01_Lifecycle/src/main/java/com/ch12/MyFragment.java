package com.ch12;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment {
	
	private TextView textView1;
	private Context context;
	
	// 回呼狀態 : Activiry 與 Fragment 建立關係
	// onAttach 所傳進來的 activity 指的就是 MainActivity.java
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//Toast.makeText(activity, ((MainActivity)activity).name, Toast.LENGTH_SHORT).show();
		System.out.println("[F] onAttach");
	}

	// 回呼狀態 : Fragment 剛被建立
	// 使用時機 : 用來設定物件變數初始值
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		System.out.println("[F] onCreate");
		
	}

	// 回呼狀態 : Fragment 即將可以顯示在螢幕時
	// 使用時機 : 用來設定 Fragment Layout 界面佈局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		System.out.println("[F] onCreateView");
		
		// 記得要 return Fragment Layout 物件
		return view;
	}

	// 回呼狀態 : 所依附的Activity onCreated()已經執行完畢
	// 使用時機 : 用來設定 Fragment (使用 getView()) 或取得 Activity (使用 getActivity()) Layout 上的 UI View 物件
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// 透過 getView() 取得 Fragment 在 onCreateView() 方法中所定義的 Layout
		textView1 = (TextView)getView().findViewById(R.id.textView1); 
		textView1.setText("Hello MyFragment");
		
		System.out.println("[F] onActivityCreated");
		
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("[F] onStart");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("[F] onResume");
	}

	// 回呼狀態 : Fragment 從螢幕上消失時
	// 使用時機 : 用來設定/儲存暫存資料
	@Override
	public void onPause() {
		super.onPause();
		System.out.println("[F] onPause");
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("[F] onStop");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		System.out.println("[F] onDestroyView");
	}

	// 回呼狀態 : Fragment Layout 佈局資料被移除
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("[F] onDestroy");
	}

	// 回呼狀態 : Fragment 已與 Activity 脫離
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		System.out.println("[F] onDetach");
	}
	
	
	
}
