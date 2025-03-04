package com.ch15;

import java.io.InputStream;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
	private Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		// 1.取得 ContentResolver 實體
		ContentResolver cp = getContentResolver();
		
		// 2.取得 URI
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		// 3.取得電話聯絡人資料 cursor
		Cursor cursor = cp.query(uri, // Uri
				null, // projection：指定欄位
				null, // selection：SQL WHERE限制式
				null, // selectionArgs：WHERE限制式?參數
				null);// order by：排序
		
		// 4.設定適配器(使用SimpleCursorAdapter)
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,
				R.layout.row, 
				cursor, 
				new String[] {
						ContactsContract.Contacts.DISPLAY_NAME,
						ContactsContract.CommonDataKinds.Phone.NUMBER,
						Contacts.Photo.PHOTO }, 
				new int[] { R.id.name, R.id.phone, R.id.photo },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		// 5.設定 ViewBinder (轉換取得聯絡人照片)
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			// 此方法可以針對特定的Cursor欄位內容來進行客製化的作業
			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				// 取得 contactId
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				if (view.getId() == R.id.photo) {
					try {
						// 根據contactId來取得該筆記錄聯絡人的照片
						InputStream inputStream = 
								ContactsContract.Contacts.openContactPhotoInputStream (
								getContentResolver(),
								ContentUris.withAppendedId (
										ContactsContract.Contacts.CONTENT_URI,
										new Long(contactId)));

						if (inputStream != null) {
							// 將照片顯示於ImageView視圖中
							Bitmap photo = BitmapFactory.decodeStream(inputStream);
							((ImageView) view).setImageBitmap(
									Bitmap.createScaledBitmap(photo, 64, 64, false));
							inputStream.close();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});
		// 6.設定適配器
		setListAdapter(adapter);
	}
}
