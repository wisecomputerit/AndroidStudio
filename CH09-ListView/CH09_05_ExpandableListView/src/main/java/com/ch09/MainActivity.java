package com.ch09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String[] menu_group = {"主餐", "副餐"};
	
	private String[][] menu_sub = {{ "牛排", "義大利麵", "咖喱飯" }, { "咖啡", "紅茶", "冰淇淋", "奶酪" }};
	
	private List<Map<String, String>> group = new ArrayList<Map<String, String>>();
	
	private List<List<Map<String, String>>> childrenList = new ArrayList<List<Map<String, String>>>();

	private ExpandableListView expListView;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		
		expListView = (ExpandableListView)findViewById(R.id.expListView);
		
		// 將 menu_group & menu_sub 資料拆解並配置到 group & childrenList
		for (int i = 0; i< menu_group.length ; i++ ) {
			// Group
			Map<String, String> groupFood = new HashMap<String, String>();
			groupFood.put("groupKey", menu_group[i]);
			group.add(groupFood);

			// Sub
			List<Map<String, String>> children = 
							new ArrayList<Map<String, String>>();
			for (String food : menu_sub[i]) {
				Map<String, String> childMap = 
							new HashMap<String, String>();
				childMap.put("childKey", food);
				children.add(childMap);
			}
			
			childrenList.add(children);
			
		}

		// 建立 SimpleExpandableListAdapter
		SimpleExpandableListAdapter mAdapter = 
							new SimpleExpandableListAdapter(
				context, 
				group, 
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "groupKey" }, 
				new int[] { android.R.id.text1 },
				childrenList, 
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "childKey" }, 
				new int[] { android.R.id.text1  }
		);
		
		// 設定Adapter
		expListView.setAdapter(mAdapter);
		
		// 設定監聽器
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, 
					View view, int groupPosition, int childPosition, long id) {
				
				String mainfood = menu_group[groupPosition];
				String subfood = menu_sub[groupPosition][childPosition];
				Toast.makeText(context, mainfood + ":" + subfood, 
						Toast.LENGTH_SHORT).show();
				return false;
			}
			
		});
		
	}
	
	

}
