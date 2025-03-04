package com.ch06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Spinner spinner1, spinner2, spinner3;
	private Context context;

	private String[] planets_array = { 
			"Mercury 水星", "Venus 金星", "Earth 地球",
			"Mars 火星", "Jupiter 木星", "Saturn 土星", 
			"Uranus 天王星", "Neptune 海王星" };

	private String[][] countries = { 
			{ "台灣", "+886" }, { "日本", "+81" }, { "韓國", "+82" }, 
			{ "泰國", "+66" }, { "印尼", "+62" } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		// XML 宣告型
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = parent.getItemAtPosition(position);
				Toast.makeText(context, "選擇：" + obj, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		// Java 實作型
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> planets_adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, planets_array);
		planets_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(planets_adapter);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = parent.getItemAtPosition(position);
				Toast.makeText(context, "選擇：" + obj, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		// Java 實作型-sub item
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (String[] country : countries) {
			Map<String, String> map = new HashMap<String, String>(2);
			map.put("country_name", country[0]);
			map.put("country_code", country[1]);
			data.add(map);
		}

		SimpleAdapter country_adapter = new SimpleAdapter(
				context, 
				data,
				android.R.layout.simple_list_item_2, 
				new String[] {"country_name", "country_code" }, 
				new int[] { android.R.id.text1, android.R.id.text2 });

		country_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(country_adapter);
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = (Map<String, String>) parent
						.getItemAtPosition(position);
				Toast.makeText(
						context,
						"country_name：" + map.get("country_name") + " "
								+ "country_code：" + map.get("country_code"),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
}