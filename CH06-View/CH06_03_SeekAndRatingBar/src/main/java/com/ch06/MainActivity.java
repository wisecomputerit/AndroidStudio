package com.ch06;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SeekBar seekBar1;
	private RatingBar ratingBar1;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			int seekBarProgress = seekBar1.getProgress();
			Toast.makeText(context, "SeekBar Progress:" + seekBarProgress,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.button2:
			int ratingBarProgress = ratingBar1.getProgress();
			Toast.makeText(context, "RatingBar Progress:" + ratingBarProgress,
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
