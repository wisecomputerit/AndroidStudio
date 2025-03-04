package com.ch08;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoWallAdapter extends BaseAdapter {

	private Context context;
	private List<Integer> photoList;

	// 優化的布林集合(key:int/value:boolean)
	// 省去傳統HashMap<Integer, Boolean>的boxing/unboxing問題
	// 提高記憶體使用上的效率
	private SparseBooleanArray mSelection;

	public PhotoWallAdapter(Context context, List<Integer> photoList) {
		mSelection = new SparseBooleanArray();
		this.context = context;
		this.photoList = photoList;
	}

	@Override
	public int getCount() {
		return photoList.size();
	}

	@Override
	public Object getItem(int position) {
		return photoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public SparseBooleanArray getMSelection() {
		return mSelection;
	}

	public void setNewSelection(int position, boolean value) {
		mSelection.put(photoList.get(position), value);
		notifyDataSetChanged();
	}

	public boolean isPositionChecked(int position) {
		Boolean result = mSelection.get(photoList.get(position));
		return result == null ? false : result;
	}

	public void removeSelection(int position) {
		mSelection.delete(photoList.get(position));
		notifyDataSetChanged();
	}

	public void clearSelection() {
		mSelection = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder gridViewImageHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.photo_layout,
					null);
			gridViewImageHolder = new ViewHolder();
			gridViewImageHolder.photo = (ImageView) view
					.findViewById(R.id.photo);
			gridViewImageHolder.mask = (View) view.findViewById(R.id.mask);
			view.setTag(gridViewImageHolder);
		} else {
			gridViewImageHolder = (ViewHolder) view.getTag();
		}
		
		gridViewImageHolder.photo.setImageResource(photoList.get(position));
		
		if (isPositionChecked(position)) {
			gridViewImageHolder.mask.setVisibility(View.VISIBLE);
		} else {
			gridViewImageHolder.mask.setVisibility(View.GONE);
		}
		return view;
	}

	static class ViewHolder {
		ImageView photo;
		View mask;
	}
}
