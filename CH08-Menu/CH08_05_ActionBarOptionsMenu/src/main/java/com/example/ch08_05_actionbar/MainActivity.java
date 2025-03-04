package com.example.ch08_05_actionbar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Context context;
	private ImageView imageView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		imageView1 = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_options_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.game1:
				imageView1.setImageResource(R.drawable.mario);
				break;
			case R.id.game2:
				imageView1.setImageResource(R.drawable.sonic);
				break;
			case R.id.menu_close:
				finish();
				break;
		}
		Toast.makeText(context, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
	    return super.onOptionsItemSelected(item);
	}
}
