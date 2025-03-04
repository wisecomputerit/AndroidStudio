package com.ch12;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.app.Activity;

public class MainActivity extends Activity {
	
	public String name = "My name is MainActivity !";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("[A] onCreate");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("[A] onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("[A] onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("[A] onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("[A] onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("[A] onDestroy");
	}
	
	
	

}
