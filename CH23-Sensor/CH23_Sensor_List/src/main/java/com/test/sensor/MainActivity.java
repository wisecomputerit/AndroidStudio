package com.test.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;
    private Context context;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        listView = (ListView) findViewById(R.id.listView);
        SensorManager sensor_manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        // 列出可用的感應器
        list = new ArrayList<String>();
        List<Sensor> sensorList = sensor_manager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor : sensorList) {
        	list.add(sensor.getName() + ":" + sensor.getPower() + "mA");
        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        setTitle("Sensor List (" + list.size() + ")");

    }
}