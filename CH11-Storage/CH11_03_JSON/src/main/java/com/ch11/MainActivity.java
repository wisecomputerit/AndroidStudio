package com.ch11;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ch11.vo.Exam;
import com.ch11.vo.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
	
	private Context context;
	private TextView textView1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        textView1 = (TextView)findViewById(R.id.textView1);
    }

	public void onClick(View view) {
    	switch(view.getId()) {
			case R.id.button1:
				// 取得 json 字串
				String json = getRowData(context, R.raw.score);
				
				// json to pojo
				Gson gson = new GsonBuilder().create();
	            Person p = gson.fromJson(json, Person.class);
				int sum = 0;
	            for(Exam e : p.getExam()) {
	            	sum += e.getScore();
				}
	            textView1.setText(json + "\n" + 
								p.getName() + " 總分:" + sum + "分");
				break;
    	}
	}
    
    private String getRowData(Context context, int res_id) {
    	
    	InputStream is = null;
    	InputStreamReader reader = null;
    	StringBuilder sb = new StringBuilder();
    	
    	try {
    		is = context.getResources().openRawResource(res_id);
    		reader = new InputStreamReader(is, "UTF-8");
    		char[] buffer = new char[1];
    		while(reader.read(buffer) != -1) {
    			sb.append(new String(buffer));
    		}
    		
    	} catch(Exception e) {
    		
    	} finally {
    		try {
    			if(is != null) is.close();
    			
    		} catch(Exception e) {}
    		
    	}
    	return sb.toString();
    }
}