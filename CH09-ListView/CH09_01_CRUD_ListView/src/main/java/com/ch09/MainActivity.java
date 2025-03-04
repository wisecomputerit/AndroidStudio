package com.ch09;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	private ArrayList<String> randomList;
	private ListAdapter adapter;
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.emptyView));
        randomList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(context, 
    									   android.R.layout.simple_expandable_list_item_1, 
    									   randomList);
    	listView.setAdapter(adapter);// 資料接口
    	
    	// 消除格線
    	listView.setDivider(null);
    	registerForContextMenu(listView);
    }
    
    
    public void onClick(View view) {
    	// 增加資料
    	randomList.add(getRandomString());
    	// 通知 adapter 更新
    	((BaseAdapter) adapter).notifyDataSetChanged();
    	// 自動將listView捲軸都移到最下面
    	listView.setSelection(adapter.getCount() - 1);
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, 
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 在 context menu 中獲取 ListView positionid 關鍵程式碼
		// 取得 menuInfo
		AdapterView.AdapterContextMenuInfo menuInfo = 
				(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		// 取得 menuInfo.position <-- 指的就是 listView 當時被點選的 position id
		int position = menuInfo.position;
        switch(item.getItemId()) {
			case R.id.show:
				Toast.makeText(context, 
						listView.getItemAtPosition(position).toString(), 
						Toast.LENGTH_LONG).show();
				break;
			case R.id.update:
				randomList.set(position, "Update：" + 
										getRandomString());
				((BaseAdapter) adapter).notifyDataSetChanged();
				break;
			case R.id.delete:
				randomList.remove(position);
				((BaseAdapter) adapter).notifyDataSetChanged();
				break;	
		}
    	return super.onContextItemSelected(item); 
	}
	
	private String getRandomString() {
		return String.valueOf((int)(Math.random() * 100000));
	}
	
    
}
