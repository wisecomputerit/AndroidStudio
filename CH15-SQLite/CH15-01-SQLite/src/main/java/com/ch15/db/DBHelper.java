package com.ch15.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "coffee.db";
	public static final int DATABASE_VERSION = 1;
	public static final String CREATE_TABLE_SQL = 
			"CREATE TABLE " +
			"coffee_list (_id INTEGER PRIMARY KEY, title TEXT, price NUMERIC, " +
			"image BLOB, created_time TIMESTAMP default CURRENT_TIMESTAMP);";
	public static final String DROP_TABLE_SQL = 
			"DROP TABLE IF EXISTS coffee_list";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onDropTable(db);
		onCreate(db);
	}

	public void onDropTable(SQLiteDatabase db) {
		db.execSQL(DROP_TABLE_SQL);
	}
}
