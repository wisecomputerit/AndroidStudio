package com.ch12;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Moto360Fragment extends Fragment {
    
	public Moto360Fragment() {

	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, 
    		ViewGroup container, Bundle savedInstanceState) {

        int resId = getArguments().getInt("resId");
		View layout = inflater.inflate(R.layout.moto360, null);
        ImageView imageView1 = (ImageView) layout
        		.findViewById(R.id.imageView1);
        imageView1.setImageResource(resId);
        
        return layout;
    }

}
