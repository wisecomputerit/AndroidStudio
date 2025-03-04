package com.ch12;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class Moto360FragmentPagerAdapter extends FragmentPagerAdapter {
    
	private int[] imageResIds;

    public Moto360FragmentPagerAdapter(FragmentManager fm, 
    		int[] imageResIds) {
        super(fm);
        this.imageResIds = imageResIds;
    }

    @Override
    public Fragment getItem(int position) {
    	
    	int resId = imageResIds[position];
    	Fragment fragment = new Moto360Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resId", resId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return imageResIds.length;
    }
   
}