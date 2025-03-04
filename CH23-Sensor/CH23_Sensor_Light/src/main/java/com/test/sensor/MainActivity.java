package com.test.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv1;
	private ImageView imageView1;
	private SensorManager sensor_manager;
	private MySensorEventListener listener;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sensor_manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		tv1 = (TextView)findViewById(R.id.msg1);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		imageView1.setVisibility(View.INVISIBLE);

		// Light傳感器
		Sensor sensor = sensor_manager.getDefaultSensor(TYPE_LIGHT);
		listener = new MySensorEventListener();
		sensor_manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_FASTEST);

	}

	// 感應器事件監聽器
	private class MySensorEventListener implements SensorEventListener {

		// 監控感應器改變
		@Override
		public void onSensorChanged(SensorEvent event) {
			StringBuilder sb = new StringBuilder();
			sb.append("sensor : " + event.sensor.getName() + "\n");
			// Android 的 Light Sensor 照度偵測內容只有 values[0] 有意義!
			final float lux = event.values[0];
			sb.append("values : " + lux + " Lux\n");
			sb.append("timestamp : " + event.timestamp + " ns");
			final String msg = sb.toString();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					tv1.setText(msg);
					if(lux <= 20) {
						imageView1.setVisibility(View.VISIBLE);
					} else {
						imageView1.setVisibility(View.INVISIBLE);
					}
				}
			});
		}

		// 對感應器精度的改變做出回應
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		sensor_manager.unregisterListener(listener);
	}
}