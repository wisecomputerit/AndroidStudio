package com.ch08;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	private Context context;
	private TextView textView1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        textView1 = (TextView) findViewById(R.id.textView1);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	/* 
    	 * 建立選單
    	 * menu.add(定義群組ID, 定義選項ID, 定義排序順位, 定義選項文字);
    	*/
    	menu.add(0, 0, Menu.NONE, "新增");
    	menu.add(0, 1, Menu.NONE, "儲存");
    	menu.add(0, 2, Menu.NONE, "查詢");
    	menu.add(0, 3, Menu.NONE, "更新");
    	menu.add(0, 4, Menu.NONE, "設定");
    	menu.add(0, 5, Menu.NONE, "返回");
    	menu.add(0, 6, Menu.NONE, "關於");
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getGroupId() == 0) {
    		textView1.setText(item.getItemId() + 
    				"：[" + item.getTitle().toString() + "]被按下了");
    	}
    	return super.onOptionsItemSelected(item);
    }
}