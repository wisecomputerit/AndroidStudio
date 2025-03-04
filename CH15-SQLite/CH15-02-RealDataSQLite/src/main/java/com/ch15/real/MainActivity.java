package com.ch15.real;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ch15.real.db.MyCursorLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends Activity 
					implements LoaderCallbacks<Cursor> {
	private Context context;
	private ListView listView = null;
	private SimpleCursorAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		listView = (ListView) findViewById(R.id.listView);
		listView.setEmptyView(findViewById(R.id.emptyView));
		// 初始化Loader
		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		final String DB_NAME = "coffee.db";
		final String DB_PATH = "/data"
	            + Environment.getDataDirectory().getAbsolutePath() + "/"
	            + getApplicationContext().getPackageName();   //在手機裡存放數據庫的位置
		SQLiteDatabase db = openDatabase(DB_PATH + "/"  + DB_NAME);
		return new MyCursorLoader(getApplicationContext(), db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter = new SimpleCursorAdapter(context, R.layout.row, data,
				new String[] { "_id", "title", "price", "image" }, new int[] {
						R.id.itemId, R.id.itemTitle, R.id.itemPrice,
						R.id.itemImage },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		listView.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

	private SQLiteDatabase openDatabase(String dbfile) {
		try  {
			// 判斷數據庫文件是否存在，若不存在則執行導入，否則直接打開數據庫
			if (!( new File(dbfile).exists())) { 
				// 取得數據庫
                InputStream is = getResources().openRawResource(R.raw.coffee_5000);
                FileOutputStream fos = new  FileOutputStream(dbfile);
                byte [] buffer = new  byte [50000];
                int  count = 0 ;
                while  ((count = is.read(buffer)) > 0 ) {
                    fos.write(buffer, 0 , count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return  db;
        } catch (Exception e) {
			e.printStackTrace();
        }
        return  null ;
    }
}
