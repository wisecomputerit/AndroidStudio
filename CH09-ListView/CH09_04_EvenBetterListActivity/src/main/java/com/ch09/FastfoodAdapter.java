package com.ch09;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FastfoodAdapter extends BaseAdapter  {
	
	// 定義 LayoutInflater
	private LayoutInflater myInflater;
	// 定義 Adapter 內藴藏的資料容器
	private ArrayList<Fastfood> list;
	
	public FastfoodAdapter(Context context, ArrayList<Fastfood> list){
		//預先取得 LayoutInflater 物件實體
        myInflater = LayoutInflater.from(context);
        this.list = list;
    }

	@Override
	public int getCount() { // 公定寫法(取得List資料筆數)
		return list.size();
	}

	@Override
	public Object getItem(int position) { // 公定寫法(取得該筆資料)
		return list.get(position);
	}

	@Override
	public long getItemId(int position) { // 公定寫法(取得該筆資料的position)
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView == null) {
			// 1:將 R.layout.row 實例化
			convertView = myInflater.inflate(R.layout.row, null);
	        
	        // 2:建立 UI 標籤結構並存放到 holder
	        holder = new ViewHolder();
	        holder.name = (TextView)convertView.findViewById(R.id.name);
	        holder.price = (TextView)convertView.findViewById(R.id.price);
	        holder.image = (ImageView)convertView.findViewById(R.id.image);
	        
	        // 3:注入 UI 標籤結構 --> convertView
	        convertView.setTag(holder);
	        
		} else {
			// 取得  UI 標籤結構
			holder = (ViewHolder)convertView.getTag();
		}

		// 4:取得Fastfood物件資料
		Fastfood fastfood = list.get(position);
		
		// 5:設定顯示資料
		holder.name.setText(fastfood.getName());
		holder.price.setText(String.valueOf(fastfood.getPrice())); // 記得要轉字串
		holder.image.setImageResource(fastfood.getImageId());
		
		return convertView;
	}
	
	// UI 標籤結構
	static class ViewHolder {
		TextView name;
		TextView price;
		ImageView image;
	}
}