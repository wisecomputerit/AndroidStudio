package com.ch08;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	private RelativeLayout layout1, layout2;
	private TextView textView1, textView2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        layout1 = (RelativeLayout) findViewById(R.id.layout1); 
        layout2 = (RelativeLayout) findViewById(R.id.layout2); 
        
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        
        //註冊Context Menu 
        registerForContextMenu(layout1); 
        registerForContextMenu(layout2);
    }
        
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, 
    		ContextMenuInfo menuInfo) { 
	    super.onCreateContextMenu(contextMenu, view, menuInfo); 
	    //設定ContextMenu選單內容 
	    switch(view.getId()) {
	    	case R.id.layout1:
	    		contextMenu.add(0, 0, 0, "咖啡"); 
	    	    contextMenu.add(0, 1, 0, "茶");
	    		break;
	    	case R.id.layout2:
	    		contextMenu.add(1, 0, 0, "蛋糕"); 
	    	    contextMenu.add(1, 1, 0, "餅乾");
		    	break;
	    }
    } 

    @Override
    public boolean onContextItemSelected(MenuItem item) { 
	    //當使用者點選項目時，所需的動作 
    	Toast.makeText(context, "您選擇的是"+item.getTitle(), 
    								Toast.LENGTH_SHORT).show(); 
    	switch(item.getGroupId()) {
	    	case 0:
	    		switch(item.getItemId()) {
			    	case 0:
			    		layout1.setBackgroundResource(R.drawable.coffee);
			    		break;
			    	case 1:
			    		layout1.setBackgroundResource(R.drawable.tea);
			    		break;	    
			    }
	    		textView1.setVisibility(View.GONE);
	    		break;
	    	case 1:
	    		switch(item.getItemId()) {
			    	case 0:
			    		layout2.setBackgroundResource(R.drawable.cake);
			    		break;
			    	case 1:
			    		layout2.setBackgroundResource(R.drawable.cookie);
			    		break;	
			    }
	    		textView2.setVisibility(View.GONE);
	    		break;	    		
    	}
	    return super.onContextItemSelected(item); 
    }
}