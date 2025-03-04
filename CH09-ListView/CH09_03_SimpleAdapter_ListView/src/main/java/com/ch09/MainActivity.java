package com.ch09;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	
	private Context context;
	private SimpleAdapter adapter;
	private ArrayList<HashMap<String, Object>> itemList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // Step 1 :定義商品資料        
        HashMap<String, Object> hamburger = new HashMap<>(); // 漢堡
        hamburger.put("name"   , "Hamburger"); // 商品名稱 
        hamburger.put("price"  , 50); // 商品價格
        hamburger.put("image", R.drawable.hamburger); // 商品圖樣Id
        
        HashMap<String, Object> french = new HashMap<>(); // 薯條
        french.put("name"   , "French fries");   
        french.put("price"  , 30);   
        french.put("image", R.drawable.french_fries);
        
        HashMap<String, Object> coca = new HashMap<>(); // 可樂
        coca.put("name"   , "Coca Cola");   
        coca.put("price"  , 20);   
        coca.put("image", R.drawable.coca_cola);
        
        // Step 2 :將商品放入到List集合容器中
        itemList = new ArrayList<>();
        itemList.add(hamburger);
        itemList.add(french);
        itemList.add(coca);
        
    	// Step 3 :建立SimpleAdapter適配器
        adapter = new SimpleAdapter(
        		context,         // 設定接口環境
        		itemList,     // 設定接口容器資料
        		R.layout.row, // 資料顯示 UI XML
        		new String[] {"name"   , "price"   , "image"},   // 商品資料標題
        		new int[]    {R.id.name, R.id.price, R.id.image} // 資料 UI
        );   
        
        // Step 4 :設定適配器
        setListAdapter(adapter);  
        
        // Step 5 :註冊 OnItemClickListener
        getListView().setOnItemClickListener(new MyOnItemClickListener());
    }
    
	// 列表項目點選之事件處理
    private class MyOnItemClickListener implements OnItemClickListener {
    	@Override
		public void onItemClick(AdapterView<?> parent, View view, 
				int position, long id) {
			// 取得被點選之商品資料
    		HashMap<String, Object> item = 
    				(HashMap<String, Object>)parent.getItemAtPosition(position);
			// 取出商品名稱, 價格
    		String msg = "您選的是:" + item.get("name") + 
    				", 價格" + item.get("price");
			// Toast 顯示
    		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		} 
    }
}

