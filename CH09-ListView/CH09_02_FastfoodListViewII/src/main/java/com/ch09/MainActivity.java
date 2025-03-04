package com.ch09;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
    
	private Context context;
	private String[] fastfood;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        fastfood = getResources().getStringArray(R.array.fastfood);
    	ListAdapter adapter = new ArrayAdapter<String>(context, 
    												   android.R.layout.simple_expandable_list_item_1, 
    												   fastfood);
    	setListAdapter(adapter);
    	getListView().setOnItemClickListener(new MyOnItemClickListener());
    }
    
    private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String key = parent.getItemAtPosition(position).toString();
			Toast.makeText(context, key, Toast.LENGTH_SHORT).show();
		}    	
    }
}

