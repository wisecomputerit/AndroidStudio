package com.ch15.cp.utils;

import com.ch15.cp.db.DBHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CoffeeProvider extends ContentProvider {
	
	public static DBHelper dbHelper;
	
	// 1.實作 onCreate()方法:建立 DBHelper
	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		return true;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	// 2.實作insert()方法:實作新增紀錄
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	// 3.實作query()方法:實作查詢法則
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables("coffee_list");
		Cursor cursor = builder.query(db, projection, selection, selectionArgs, 
				null, null, sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
