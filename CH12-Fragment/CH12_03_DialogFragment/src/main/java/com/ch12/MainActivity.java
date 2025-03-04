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
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		switch(view.getId()) {
			case R.id.buttonA:
				ft.replace(R.id.fragment_addin_linearlayout, new MyFragmentA());
				break;
			case R.id.buttonB:
				ft.replace(R.id.fragment_addin_linearlayout, new MyFragmentB());
				break;
		}
		
		
		ft.commit();
		
	}

}
