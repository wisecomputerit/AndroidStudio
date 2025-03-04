package com.ch15.utils;

import java.util.List;

import com.ch15.R;
import com.ch15.vo.CoffeeItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoffeeItemAdapter extends BaseAdapter {

	private LayoutInflater myInflater;
	private List<CoffeeItem> list;
	
	public CoffeeItemAdapter(Context context, List<CoffeeItem> list) {
		myInflater = LayoutInflater.from(context);
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public int getPosition(String title) {
		for(int i=0;i<list.size();i++) {
			CoffeeItem coffee = list.get(i);
			if(coffee.getTitle().equals(title)) {
				return i;
			}
		}
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = myInflater.inflate(R.layout.coffee_row, null);
		TextView coffee_title = (TextView)convertView.findViewById(R.id.coffee_title);
        ImageView coffee_image = (ImageView)convertView.findViewById(R.id.coffee_image);
        CoffeeItem coffee = list.get(position);
        
        coffee_title.setText(coffee.getTitle());
        coffee_image.setImageResource(coffee.getImage());
        
		return convertView;
	}

}
