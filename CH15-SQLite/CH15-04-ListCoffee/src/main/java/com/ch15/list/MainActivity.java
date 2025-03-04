package com.ch15.list;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {
	private Context context;
	private ListView listView = null;

	private Uri coffeeURI = Uri
			.parse("content://com.ch15.cp.utils.CoffeeProvider");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		listView = (ListView) findViewById(R.id.listView);
		listView.setEmptyView(findViewById(R.id.emptyView));

		// 重新整理ListView
		refreshListView();
	}

	// 重新整理ListView（將資料重新匯入）
	private void refreshListView() {

		Cursor cursor = getContentResolver().query(
				coffeeURI,
				new String[] { "_id", "title", "price", "image" }, 
				null, null, null);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				context,
				R.layout.row, 
				cursor, 
				new String[] { "_id", "title", "price", "image" }, 
				new int[] {R.id.itemId, 
						R.id.itemTitle, R.id.itemPrice, R.id.itemImage },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		listView.setAdapter(adapter);

	}

}
