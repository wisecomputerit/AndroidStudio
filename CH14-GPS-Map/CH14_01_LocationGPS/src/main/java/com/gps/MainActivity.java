package com.gps;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/*******************************************************
	 * 定位提供者 GPS : LocationManager.GPS_PROVIDER Network :
	 * LocationManager.NETWORK_PROVIDER
	 *******************************************************/
	public final String LM_GPS = LocationManager.GPS_PROVIDER;
	public final String LM_NETWORK = LocationManager.NETWORK_PROVIDER;
	
	private Context context;
	private TextView textView1;
	// 定位管理器
	private LocationManager mLocationManager;
	// 定位監聽器
	private LocationListener mLocationListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		mLocationManager = 
				(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationListener = new MyLocationListener();
		textView1 = (TextView) findViewById(R.id.textView1);
		openGPS(context);
	}

	// 在 resume 階段設定 mLocationListener 監聽器，以獲得地理位置的更新資料
	@Override
	protected void onResume() {

		if (mLocationManager == null) {
			mLocationManager = 
					(LocationManager) getSystemService(Context.LOCATION_SERVICE);
			mLocationListener = new MyLocationListener();
		}
		// 獲得地理位置的更新資料 (GPS 與 NETWORK都註冊)
		mLocationManager
				.requestLocationUpdates(LM_GPS, 0, 0, mLocationListener);
		mLocationManager.requestLocationUpdates(LM_NETWORK, 0, 0,
				mLocationListener);
		setTitle("onResume ...");
		super.onResume();
	}

	// 在 pause 階段關閉 mLocationListener 監聽器不再獲得地理位置的更新資料
	@Override
	protected void onPause() {
		if (mLocationManager != null) {
			// 移除 mLocationListener 監聽器
			mLocationManager.removeUpdates(mLocationListener);
			mLocationManager = null;
		}
		setTitle("onPause ...");
		super.onPause();
	}

	// 開啟 GPS
	public void openGPS(Context context) {
		boolean gps = mLocationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean network = mLocationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Toast.makeText(context, "GPS : " + gps + ", Network : " + network,
				Toast.LENGTH_SHORT).show();
		if (gps || network) {
			return;
		} else {
			// 開啟手動GPS設定畫面
			Intent gpsOptionsIntent = new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(gpsOptionsIntent);
		}
	}

	public void onClick(View view) {
		openGPS(context);
	}

	// 定位監聽器實作
	private class MyLocationListener implements LocationListener {
		// GPS位置資訊已更新
		public void onLocationChanged(Location location) {

			textView1.setText("Location-GPS" + "\n" + 
					"緯度-Latitude：" + location.getLatitude() + "\n" +
					"經度-Longitude：" + location.getLongitude() + "\n" + 
					"精確度-Accuracy：" + location.getAccuracy() + "\n" +
					"標高-Altitude：" + location.getAltitude() + "\n" + 
					"時間-Time：" + new Date(location.getTime()) + "\n" + 
					"速度-Speed：" + location.getSpeed() + "\n" + 
					"方位-Bearing：" + location.getBearing());
			setTitle("GPS位置資訊已更新");
		}
		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}
		// GPS位置資訊的狀態被更新
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			case LocationProvider.AVAILABLE:
				setTitle("服務中");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				setTitle("沒有服務");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				setTitle("暫時不可使用");
				break;
			}
		}
	}
}