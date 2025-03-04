package com.ch12;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LottoFragment extends Fragment {
	
	private TextView lotto_num;
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
		
		View view = inflater.inflate(R.layout.fragment_lotto, container, false);
		
		// 記得要 return Fragment Layout 物件
		return view;
	}

	// 回呼狀態 : 可以開始取得 Fragment Layout 界面物件
	// 使用時機 : 用來設定 Layout 上的 UI View 物件
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// 透過 getView() 取得 Fragment 在 onCreateView() 方法中所定義的 Layout
		lotto_num = (TextView)getView().findViewById(R.id.lotto_num); 
		int lottoNumber = (int)(Math.random() * 100 % 50);
		lotto_num.setText(String.valueOf(lottoNumber));
		
	}
	
	// 回呼狀態 : Fragment 從螢幕上消失時
	// 使用時機 : 用來設定/儲存暫存資料
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
}
