package com.ch07;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyFrameLayout extends Activity {
	
	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
    }
    
    public void onClick(View view) {
    	
    	switch(view.getId()) {
    		case R.id.button1:
    		case R.id.button2:
    		case R.id.button3:
    			view.setVisibility(View.GONE);
    			break;
    		case R.id.button4:
    			Toast.makeText(context, "Finish !", Toast.LENGTH_SHORT).show();
    			finish();	
        			
    	}
    }
}