package com.ch17;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    // 取得管理資源
    private ActivityManager manager;
    // 存放所有執行中的服務
    private List<ActivityManager.RunningServiceInfo> serviceList;
    private Context context;
    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setSelected(true);

        // 取得服務管理實例
        manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        // 取得所有執行中服務
        serviceList = manager.getRunningServices(20); // 最大顯示個數20筆
        // 顯示所有 running 服務列表
        showServiceList();
    }

    // 顯示所有 running 服務列表
    private void showServiceList() {
        ArrayList<Map<String, String>> list = getServiceList();
        SimpleAdapter adapter = new SimpleAdapter(
                context,
                list,
                android.R.layout.simple_list_item_2,
                new String[]{"ShortClassName", "PackageName"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView1.setAdapter(adapter);
    }

    // 判斷所指定之服務資源是否正在執行 ?
    private boolean checkServiceIsStarted(String className) {
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if (className.equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // 取得 Running Service 資源名稱(short class name + package name)
    private ArrayList<Map<String, String>> getServiceList() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("ShortClassName", info.service.getShortClassName());
            item.put("PackageName", info.service.getPackageName());
            list.add(item);
        }
        return list;
    }
}
