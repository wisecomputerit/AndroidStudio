package com.ch12;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View view) {
		// 建立 Fragment 交易服務機制
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch(view.getId()) {
			case R.id.buttonA:
				// 置換 fragment
				ft.replace(R.id.fragment_addin_linearlayout, 
						new MyFragmentA(), "f_a");
				break;
			case R.id.buttonB:
				// 置換 fragment
				ft.replace(R.id.fragment_addin_linearlayout, 
						new MyFragmentB(), "f_b");
				break;
		}
		// 提交
		ft.commit();
	}
}
