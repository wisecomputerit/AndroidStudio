package com.ch05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText bmi_height, bmi_weight;
	private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        bmi_height = (EditText) findViewById(R.id.bmi_height);
        bmi_weight = (EditText) findViewById(R.id.bmi_weight);
    }
    public void onClick(View view) {
    	try {
	    	int height = Integer.parseInt(bmi_height.getText().toString());
	    	int weight = Integer.parseInt(bmi_weight.getText().toString());
	    	
	    	Bundle extras = new Bundle();
	    	extras.putInt("height", height);
	    	extras.putInt("weight", weight);
	    	
	    	Intent intent = new Intent(context, BMIActivity.class);
	    	intent.putExtras(extras);
	    	startActivity(intent);
    	} catch(Exception e) {
    		Toast.makeText(context, R.string.bmi_error_message, 
    				Toast.LENGTH_SHORT).show();
    	}
    }
}
