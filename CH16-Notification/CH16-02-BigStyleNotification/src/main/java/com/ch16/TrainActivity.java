package com.ch16;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class TrainActivity extends Activity {
	private ImageView imageView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train);
    }
}
