package com.ch08;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView1, textView2, textView3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		textView1 = (TextView)findViewById(R.id.textView1);
		textView2 = (TextView)findViewById(R.id.textView2);
		textView3 = (TextView)findViewById(R.id.textView3);
		
		/*
		 * 使用addSubMenu來建立有子選單的選單 menu.addSubMenu(定義群組ID, 定義選項ID, 定義排序順位,
		 * 定義選項文字);
		 */
		SubMenu menu1 = menu.addSubMenu(0, 0, Menu.NONE, "茶品");
		SubMenu menu2 = menu.addSubMenu(0, 0, Menu.NONE, "咖啡");
		SubMenu menu3 = menu.addSubMenu(0, 0, Menu.NONE, "冷熱");

		// 建立 [茶品] 的子選單
		menu1.add(1, 0, Menu.NONE, "紅茶");
		menu1.add(1, 1, Menu.NONE, "奶茶");
		menu1.add(1, 2, Menu.NONE, "綠茶");
		// 加入Radio樣式
		menu1.setGroupCheckable(1, true, true);
		
		// 建立 [咖啡] 的子選單
		menu2.add(2, 0, Menu.NONE, "拿鐵");
		menu2.add(2, 1, Menu.NONE, "焦糖");
		menu2.add(2, 2, Menu.NONE, "卡布奇諾 ");
		menu2.setGroupCheckable(2, true, true);

		// 建立 [冷熱] 的子選單
		menu3.add(3, 0, Menu.NONE, "冷");
		menu3.add(3, 1, Menu.NONE, "熱");
		menu3.setGroupCheckable(3, true, true);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean flag = !item.isChecked();
		item.setChecked(flag);
		
		switch (item.getGroupId()) {
			case 1:
				if(flag) textView1.setText(item.getTitle().toString());
				else textView1.setText("");
				break;
			case 2:
				if(flag) textView2.setText(item.getTitle().toString());
				else textView2.setText("");
				break;
			case 3:
				if(flag) textView3.setText(item.getTitle().toString());
				else textView3.setText("");
				break;	
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}