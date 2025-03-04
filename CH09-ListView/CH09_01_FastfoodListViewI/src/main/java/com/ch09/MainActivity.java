package com.ch09;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	
	private Context context;
	private String[] fastfood;
	private ListAdapter adapter;
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyView));
        fastfood = getResources().getStringArray(R.array.fastfood);
    	adapter = new ArrayAdapter<String>(context, 
    									   android.R.layout.simple_expandable_list_item_1, 
    									   fastfood);
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(new MyOnItemClickListener());
    }
    
    //按下Item監聽器
    private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 取得Item內容
			String content = parent.getItemAtPosition(position).toString();
			Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
		}    	
    }
    
}
