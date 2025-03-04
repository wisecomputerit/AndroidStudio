package com.ch09;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<MyAdapter.ViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // 若設為FixedSize可以增加效率不過就喪失了彈性
        mRecyclerView.setHasFixedSize(true);

        // 選擇一種Layout管理器這邊是選擇（linear layout manager）
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] myDataset = getResources().getStringArray(R.array.languages);
        // 設定適配器
        mAdapter = new MyAdapter(context, myDataset);
        mRecyclerView.setAdapter(mAdapter);
        
    }
    
}
