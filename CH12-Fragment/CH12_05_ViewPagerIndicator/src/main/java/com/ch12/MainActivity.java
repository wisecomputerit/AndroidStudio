package com.ch12;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends FragmentActivity {
	
	private Moto360FragmentPagerAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mAdapter = new Moto360FragmentPagerAdapter(
        		getSupportFragmentManager(), Data.MOTO360);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        
        mIndicator.setViewPager(mPager);
    }
}