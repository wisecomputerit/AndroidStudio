package com.ch06;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.aphidmobile.flip.FlipViewController;

public class FlipExpressActivity extends Activity {

	private FlipViewController flipView;

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		List<Express> list = new ArrayList<Express>();
		list.add(new Express(R.string.puyuma_express_imageurl, R.string.puyuma_express));
		list.add(new Express(R.string.taroko_express_imageurl, R.string.taroko_express));
		list.add(new Express(R.string.jr_885_express_imageurl, R.string.jr_885_express));
		
		flipView = new FlipViewController(context, FlipViewController.VERTICAL);
		flipView.setAdapter(new ExpressAdapter(context, list));

		setContentView(flipView);

	}

	@Override
	protected void onResume() {
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}
}
