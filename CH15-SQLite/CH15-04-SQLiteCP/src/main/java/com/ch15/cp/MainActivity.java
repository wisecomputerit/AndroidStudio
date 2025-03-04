package com.ch15.cp;

import java.util.ArrayList;
import java.util.List;

import com.ch15.cp.db.DBHelper;
import com.ch15.cp.utils.CoffeeItemAdapter;
import com.ch15.cp.vo.CoffeeItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context context;
	private ListView listView = null;
	private ImageButton fab;
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private SimpleCursorAdapter adapter;
	private Cursor maincursor; // 記錄目前資料庫查詢指標

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();

		listView = (ListView) findViewById(R.id.listView);
		listView.setEmptyView(findViewById(R.id.emptyView));
		listView.setOnItemClickListener(new MyOnItemClickListener());

		fab = (ImageButton) findViewById(R.id.fab);
		// 實作視圖外觀
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
			@Override
			public void getOutline(View view, Outline outline) {
				// 設定視圖尺寸
				int size = getResources().getDimensionPixelSize(
						R.dimen.fab_size);
				// 將視圖外觀設成橢圓形（本例是設成圓形）
				outline.setOval(0, 0, size, size);
			}
		};
		// 設定 fab（ImageButton）外框樣式
		fab.setOutlineProvider(viewOutlineProvider);
		// 註冊 fab onclick 事件
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addCoffeeDialog();
			}
		});
		// 重新整理ListView
		refreshListView();
	}

	// 重新整理ListView（將資料重新匯入）
	private void refreshListView() {
		if (maincursor == null) {
			// 1.取得查詢所有資料的cursor
			maincursor = db.rawQuery(
					"SELECT _id, title, price, image FROM coffee_list", null);
			// 2.設定ListAdapter適配器(使用SimpleCursorAdapter)
			adapter = new SimpleCursorAdapter(context, R.layout.row,
					maincursor,
					new String[] { "_id", "title", "price", "image" },
					new int[] { R.id.itemId, R.id.itemTitle, R.id.itemPrice,
							R.id.itemImage },
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			// 3.注入適配器
			listView.setAdapter(adapter);
		} else {
			if (maincursor.isClosed()) { // 彌補requery()不會檢查cursor closed的問題
				maincursor = null;
				refreshListView();
			} else {
				maincursor.requery(); // 若資料龐大不建議使用此法（應改用 CursorLoader）
				adapter.changeCursor(maincursor);
				adapter.notifyDataSetChanged();
			}
		}
	}

	// 新增商品記錄對話視窗
	private void addCoffeeDialog() {
		// 自定Layout
		LayoutInflater inflater = getLayoutInflater();
		// 將 xml layout 轉換成視圖 View 物件
		View layout = inflater.inflate(R.layout.form,
				(ViewGroup) findViewById(R.id.root));

		// 自定View
		final Spinner coffee_spinner = (Spinner) layout
				.findViewById(R.id.coffee_spinner);
		final TextView coffee_price = (TextView) layout
				.findViewById(R.id.coffee_price);
		coffee_spinner.setAdapter(new CoffeeItemAdapter(context,
				getCoffeeList()));

		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.append_product);
		builder.setIcon(android.R.drawable.ic_input_add);
		builder.setView(layout);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						CoffeeItem coffee = (CoffeeItem) coffee_spinner
								.getSelectedItem();

						// 將記錄新增到coffee資料表的參數
						ContentValues cv = new ContentValues();
						cv.put("title", coffee.getTitle());
						cv.put("price", Integer.parseInt(coffee_price.getText().toString()));
						cv.put("image", coffee.getImage());
							
						// 執行SQL語句
						long id = db.insert("coffee_list", null, cv);
						Toast.makeText(context, "_id：" + id, Toast.LENGTH_SHORT).show();
						
						// 將資料匯入ListView
						refreshListView();
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	// 修改/刪除商品對話視窗
	private void modifyCoffeeDialog(final Cursor cursor) {
		// 自定Layout
		LayoutInflater inflater = getLayoutInflater();
		// 將 xml layout 轉換成視圖 View 物件
		View layout = inflater.inflate(R.layout.form,
				(ViewGroup) findViewById(R.id.root));

		// 自定View
		final Spinner coffee_spinner = (Spinner) layout
				.findViewById(R.id.coffee_spinner);
		final TextView coffee_price = (TextView) layout
				.findViewById(R.id.coffee_price);

		CoffeeItemAdapter coffeeAdapter = new CoffeeItemAdapter(context,
				getCoffeeList());
		coffee_spinner.setAdapter(coffeeAdapter);

		coffee_price.setText(cursor.getString(2));
		coffee_spinner.setSelection(coffeeAdapter.getPosition(cursor
				.getString(1)));

		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.update_delete_product);
		builder.setIcon(android.R.drawable.ic_input_add);
		builder.setView(layout);
		builder.setPositiveButton(R.string.modify,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						CoffeeItem coffee = (CoffeeItem) coffee_spinner
								.getSelectedItem();

						// 將修改記錄回存到coffee資料表中指定資料列
						ContentValues cv = new ContentValues();
						cv.put("title", coffee.getTitle());
						cv.put("price", Integer.parseInt(coffee_price.getText().toString()));
						cv.put("image", coffee.getImage());
							
						// 執行SQL修改語句
						int rowcount = db.update("coffee_list", cv, "_id=?", 
								new String[]{cursor.getString(0)});
						Toast.makeText(context, "異動筆數：" + rowcount, Toast.LENGTH_SHORT).show();
						
						refreshListView();
						dialog.dismiss();
					}
				});
		builder.setNeutralButton(R.string.delete,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 執行SQL刪除語句，將刪除coffee資料表中指定資料列
						int rowcount = db.delete("coffee_list", "_id=?", 
								new String[]{cursor.getString(0)});
						Toast.makeText(context, "異動筆數：" + rowcount, Toast.LENGTH_SHORT).show();
						
						refreshListView();
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}

	// 取得Coffee資料列表
	private List<CoffeeItem> getCoffeeList() {
		List<CoffeeItem> data = new ArrayList<CoffeeItem>();
		data.add(new CoffeeItem("Latte", R.drawable.coffee_latte));
		data.add(new CoffeeItem("Cappuccino", R.drawable.coffee_cappuccino));
		data.add(new CoffeeItem("Macchiato", R.drawable.coffee_macchiato));
		data.add(new CoffeeItem("Mocha", R.drawable.coffee_mocha));
		return data;
	}

	// OnItemClick 監聽器
	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 取得 Cursor
			Cursor cursor = (Cursor) parent.getItemAtPosition(position);
			modifyCoffeeDialog(cursor);
		}
	}
}
