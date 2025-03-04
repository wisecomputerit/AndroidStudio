package com.test.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv1;
	private Handler handler;
	private SensorManager sensor_manager;
	private MySensorEventListener listener;

	private MyLinearLayout layout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sensor_manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		tv1 = (TextView)findViewById(R.id.msg1);

		layout = (MyLinearLayout)findViewById(R.id.myLinearLayout);
		layout.setWillNotDraw(false);
		layout.invalidate();
		handler = new Handler();

		// 重力加速度感應器
		Sensor sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		listener = new MySensorEventListener();
		sensor_manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

	}

	// 重力加速度感應器事件監聽器
	private class MySensorEventListener implements SensorEventListener {

		// 監控感應器改變
		@Override
		public void onSensorChanged(SensorEvent event) {
			StringBuilder sb = new StringBuilder();
			sb.append("sensor : " + event.sensor.getName() + "\n");
			sb.append("accuracy : " + getAccuracyName(event.accuracy) + "\n");
			// Android 的 Accelerometer Sensor 測量接觸力, 以SI(m/s^2)為單位!
			// - 當設備從左邊移向右邊時, X為負加速度值.
			// - 當設備從底邊移向頂邊時, Y為負加速度值.
			// - 當設備從正面移向背面時, Z為負加速度值.
			sb.append("values : \n");
			sb.append("X:" + event.values[0] + "\n"); // X 測量接觸力
			sb.append("Y:" + event.values[1] + "\n"); // Y 測量接觸力
			sb.append("Z:" + event.values[2] + "\n"); // Z 測量接觸力

			sb.append("timestamp : " + event.timestamp + " ns");
			final String msg = sb.toString();

			layout.setPosition(event.values[0], event.values[1]);

			System.out.println(msg);
			handler.post(new Runnable() {

				@Override
				public void run() {
					tv1.setText(msg);

				}

			});
		}

		// 對感應器精度的改變做出回應
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {


		}

		// 取得 accuracy 對應的訊息內容
		private String getAccuracyName(int accuracy) {
			String name = "";
			switch(accuracy) {
				case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
					name = "LOW";
					break;
				case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
					name = "MEDIUM";
					break;
				case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
					name = "HIGH";
					break;
				case SensorManager.SENSOR_STATUS_UNRELIABLE:
					name = "UNRELIABLE";
					break;
				default:
					name = "Non";
			}
			return name;
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		sensor_manager.unregisterListener(listener);
	}
}