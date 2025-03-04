package com.test.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv1;

	private SensorManager sensor_manager;
	private MySensorEventListener listener;
	private MyImageView iv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		tv1 = (TextView) findViewById(R.id.msg1);
		iv = (MyImageView) findViewById(R.id.imageView1);

		// 方向偵測器
		Sensor aSensor = sensor_manager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		Sensor mfSensor = sensor_manager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		listener = new MySensorEventListener();
		sensor_manager.registerListener(listener, aSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensor_manager.registerListener(listener, mfSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	private class MySensorEventListener implements SensorEventListener {

		private float[] accelerometerValues;
		private float[] magneticFieldValues;

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				accelerometerValues = (float[]) event.values.clone();
			} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				magneticFieldValues = (float[]) event.values.clone();
			}

			if (accelerometerValues != null && magneticFieldValues != null) {


				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// 計算方位
						calculateOrientation();

					}

				});


			}
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		// 計算方位
		private void calculateOrientation() {

			//(-180~180) 0:正北，90:正東，180/-180:正南，-90:正西
			float[] values = new float[3];

			float[] inR = new float[9];
			SensorManager.getRotationMatrix(inR, null, accelerometerValues, magneticFieldValues);

			// 利用重映方向參考坐標系 (非必要)
			float[] outR = new float[9];
			SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);

			SensorManager.getOrientation(inR, values); // 第一個參數可以置換 inR 或 outR


			values[0] = (float) Math.toDegrees(values[0]);
			float degress = values[0];
			tv1.setText("Degress:" + degress);
			if (values[0] >= -5 && values[0] < 5) {
				tv1.setText("Degress:" + degress + "\n正北");
			} else if (values[0] >= 40 && values[0] < 50) {
				tv1.setText("Degress:" + degress + "\n東北");
			} else if (values[0] >= 85 && values[0] <= 95) {
				tv1.setText("Degress:" + degress + "\n正東");
			} else if (values[0] >= 130 && values[0] < 140) {
				tv1.setText("Degress:" + degress + "\n東南");
			} else if ((values[0] >= 175 && values[0] <= 180)
					|| (values[0]) >= -180 && values[0] < -175) {
				tv1.setText("Degress:" + degress + "\n正南");
			} else if (values[0] >= -140 && values[0] < -130) {
				tv1.setText("Degress:" + degress + "\n西南");
			} else if (values[0] >= -95 && values[0] < -85) {
				tv1.setText("Degress:" + degress + "\n正西");
			} else if (values[0] >= -50 && values[0] < -40) {
				tv1.setText("Degress:" + degress + "\n西北");
			}
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		sensor_manager.unregisterListener(listener);
	}
}