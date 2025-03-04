package com.ch12;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentB extends Fragment {
	
	private TextView textView1;
	private Button button1;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_b, container, false);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		button1 = (Button)getView().findViewById(R.id.button1); 
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView pincode = (TextView) getActivity().findViewById(R.id.pincode);
				Toast.makeText(context, pincode.getText(), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
}
