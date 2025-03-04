package com.example;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private Context context;
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList; // 左邊選單List
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle; // 抽屜 title
    private CharSequence mTitle; // Activity title
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // 設定 mDrawerList's Adapter listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(context, 
        		R.layout.drawer_list_item, Planet.planetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // 將導航抽屜選單作用在ActionBar上
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // 設定 ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* 主要 Activity */
	                mDrawerLayout,         /* DrawerLayout xml */
	                R.string.drawer_open,  /* 開啓抽屜簡述 */
	                R.string.drawer_close  /* 關閉抽屜簡述 */
                ) {
            public void onDrawerClosed(View view) {
            	// 設定 action bar title
                getActionBar().setTitle(mTitle);
            }
            public void onDrawerOpened(View drawerView) {
            	// 設定 action bar title
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        // ActionBarDrawerToggle 狀態與 DrawerLayout 同步
        mDrawerToggle.syncState();
        // DrawerLayout設定DrawerToggle監聽器
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 預設顯示 position = 2 的資料內容  
        if (savedInstanceState == null) {
            selectItem(2);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 打開/關閉抽屜與打開方向
    	if(mDrawerLayout.isDrawerOpen(Gravity.START)) {
    		mDrawerLayout.closeDrawer(Gravity.START);
    	} else {
    		mDrawerLayout.openDrawer(Gravity.START);
    	}
    	return super.onOptionsItemSelected(item);
    }

    // 抽屜項目列表監聽器    
    private class DrawerItemClickListener 
    					implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, 
        						int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // 建立 Fragment
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, 
        				fragment).commit();

        // 設定是否要表現出項目被選定的樣式
        mDrawerList.setItemChecked(position, true);
        // 設定 Activity title
        setTitle(Planet.planetTitles[position]);
        // 關閉抽屜
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}