package com.gcm.parse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Context context;
    private List<Integer> list;
    private ArrayAdapter<Integer> adapter;
    private GridView gridView;
    private BingoReceiver receiver;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        list = new ArrayList<>();
        for(int i=0;i<25;i++) {
            list.add((i+1));
        }

        adapter = new ArrayAdapter<Integer>(context, R.layout.row, list);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);


        receiver = new BingoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.parse.push.intent.RECEIVE");
        registerReceiver(receiver, filter);

    }

    private void shuffle() {
        Collections.shuffle(list);
        adapter.notifyDataSetChanged();
    }

    class BingoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                num = Integer.parseInt(json.getString("alert"));

                if(num == -1) { // 傳入 -1 結束遊戲
                    finish();
                    return;
                }

                if(num == 0) { // 傳入 0 遊戲重新玩 reset
                    for(int i=0;i<list.size();i++) {
                        getViewByPosition(i).setBackgroundColor(Color.WHITE);
                    }
                    return;
                }

                for(int i=0;i<list.size();i++) {
                    if(list.get(i) == num) {
                        getViewByPosition(i).setBackgroundColor(Color.RED);
                        break;
                    }
                }

            } catch (Exception e) {

            }
        }
    }

    private View getViewByPosition(int position) {
        int firstPosition = gridView.getFirstVisiblePosition();
        int lastPosition = gridView.getLastVisiblePosition();

        if ((position < firstPosition) || (position > lastPosition))
            return null;

        return gridView.getChildAt(position - firstPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            shuffle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
