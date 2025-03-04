package com.ch12;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentA extends Fragment {
	
	private Context context;
	private Handler handler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		handler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_a, container, false);
		
		return view;
	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView pincode = (TextView)getView().findViewById(R.id.pincode); 
		showPinCode(pincode);
		
	}
	
	private void showPinCode(final TextView pincode) {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				int pcode = (int)(Math.random() * 1000);
				pincode.setText(String.valueOf(pcode));
				showPinCode(pincode);
			}
			
		}, 3000);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
}
