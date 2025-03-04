package com.ch09;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	
	private Context context;
	private ArrayList<Fastfood> listItem;
	private FastfoodAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        // Step 1 :定義商品資料 for JavaBean       
        Fastfood famburger = new Fastfood("Hamburger", 50, R.drawable.hamburger);
        Fastfood french = new Fastfood("French fries", 30, R.drawable.french_fries);
        Fastfood coca = new Fastfood("Coca Cola", 20, R.drawable.coca_cola);
        
        // Step 2 :將商品放入到List集合容器中
        listItem = new ArrayList<Fastfood>();
        listItem.add(famburger);
        listItem.add(french);
        listItem.add(coca);
        
    	// Step 3 :建立 FastfoodAdapter 適配器
        adapter = new FastfoodAdapter(context, listItem);
        
        // Step 4 :設定適配器
        setListAdapter(adapter);  
        
        // Step 5 :註冊 OnItemClickListener
        getListView().setOnItemClickListener(new MyOnItemClickListener());
    }
	
    // 列表項目點選之事件處理
    private class MyOnItemClickListener implements OnItemClickListener {
    	@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 取得被點選之商品資料
    		Fastfood coffee = (Fastfood)parent.getItemAtPosition(position);
			// 取出商品名稱, 價格
    		String msg = "您選的是:" + coffee.getName() + ", 價格" + coffee.getPrice();
    		// Toast 顯示
    		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

		} 
    }
}

