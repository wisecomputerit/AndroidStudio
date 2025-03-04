package com.ch12;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);

		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

		if (findViewById(R.id.fragment_container) != null) {
			HeadlinesFragment headlinsFragment = new HeadlinesFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, headlinsFragment).commit();
		}
	}
}