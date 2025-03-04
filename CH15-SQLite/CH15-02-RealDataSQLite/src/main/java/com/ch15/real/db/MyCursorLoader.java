package com.ch15.real.db;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyCursorLoader extends CursorLoader {
	private SQLiteDatabase db;

	public MyCursorLoader(Context context, SQLiteDatabase db) {
		super(context);
		this.db = db;
	}

	@Override
	protected Cursor onLoadInBackground() {
		Cursor cursor = db.rawQuery(
				"SELECT _id, title, price, image FROM coffee_list", null);
		return cursor;
	}
}
