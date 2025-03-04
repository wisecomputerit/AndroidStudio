package com.ch06;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.ch06.utils.VolleySingleton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpressAdapter extends BaseAdapter {
	
	private List<Express> list;
	private LayoutInflater inflater;
	private Context context;
	
	
	public ExpressAdapter(Context context, List<Express> list) {
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
		this.context = context;
			
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		// View Holder Pattern
		if(convertView==null){
			convertView = inflater.inflate(R.layout.activity_main, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.express_content = (TextView)convertView.findViewById(R.id.express_content);
			viewHolder.express_imageView = (ImageView)convertView.findViewById(R.id.express_imageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Express express = list.get(position);
		
		// Volley imageloader
		ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(viewHolder.express_imageView,
				R.drawable.ic_loading, R.drawable.ic_error);
		imageLoader.get(context.getResources().getString(express.getImageURLId()), listener);
		
		viewHolder.express_content.setText(context.getResources().getString(express.getContentId()));
		
		return convertView;
	}

	
	private static class ViewHolder {
		TextView express_content;
		ImageView express_imageView;
	}
}
