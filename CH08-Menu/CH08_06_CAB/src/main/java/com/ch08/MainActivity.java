package com.ch08;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridView mPhotoWall;
	private PhotoWallAdapter adapter;
	private Context context;
	private List<Integer> photoList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		
		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
		photoList = Photo.getPhotoList();
		adapter = new PhotoWallAdapter(context, photoList);
		mPhotoWall.setAdapter(adapter);
		
		mPhotoWall.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		mPhotoWall.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			private int checkedcount = 0;
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				SparseBooleanArray sba = adapter.getMSelection();
				switch (item.getItemId()) {
                
	                case R.id.item_delete:
	                	
	                	for(int index=0;index<sba.size();index++) {
	                		for(int i=0;i<photoList.size();i++) {
	                			int imageResId = photoList.get(i);
                				if(imageResId == sba.keyAt(index)) {
                					photoList.remove(i);
                					break;
                				}
                			}
                			adapter.notifyDataSetChanged();
						}
	                	
	                	checkedcount = 0; // 選取資料歸0
	        			adapter.clearSelection(); // 清除選取
	        			mode.finish(); // 結束 CAB 狀態
	                    
	                    break;
	                    
	                case R.id.item_share:
	                	
	                	ArrayList<Uri> imageUris = new ArrayList<Uri>();
	                	for(int index=0;index<sba.size();index++) {
	                		for(int i=0;i<photoList.size();i++) {
	                			int imageResId = photoList.get(i);
                				if(imageResId == sba.keyAt(index)) {
                					Uri uri = Uri.parse("android.resource://" + 
                							context.getPackageName() + "/" + imageResId);
    	                			imageUris.add(uri);
                					break;
                				}
                			}
                			adapter.notifyDataSetChanged();
						}
	                	
	                	Intent shareIntent = new Intent();
	                	shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
	                	shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, 
	                										imageUris);
	                	shareIntent.setType("image/*");
	                	startActivity(Intent.createChooser(shareIntent, 
	                				"Share images to.."));
	                	
	                	break;
				}
				return false;
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual_menu, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				checkedcount = 0;
				adapter.clearSelection();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, 
					long id, boolean checked) {
				if (checked) {
					checkedcount++;
					adapter.setNewSelection(position, checked);                    
                } else {
                	checkedcount--;
                	adapter.removeSelection(position);                 
                }
				mode.setSubtitle("sub title");
                mode.setTitle(checkedcount + " selected");
			}
		});
	}
}
