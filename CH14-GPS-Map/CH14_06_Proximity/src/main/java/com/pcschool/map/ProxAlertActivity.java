package com.pcschool.map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProxAlertActivity extends FragmentActivity {

    private GoogleMap mMap;
    // 最短距離(公尺單位)
    private final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;
    // 最小時間(微秒單位)
    private final long MINIMUM_TIME_BETWEEN_UPDATE = 1000;
    // 預設地界半徑公尺單位
    private final long POINT_RADIUS = 100;
    // 預設地界中心坐標
    private final LatLng DEFAULT_TTS = new LatLng(25.0417443, 121.5503917);
    // 偵測有效期限, -1表示永久有效
    private final long PROX_ALERT_EXPIRATION = -1;
    private final String PROX_ALERT_INTENT =  "This.is.my.ProximityAlert";
    private final NumberFormat nf = new DecimalFormat("###,###.##");
    private LocationManager locationManager;
    private ProximityIntentReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATE,
                MINIMUM_DISTANCECHANGE_FOR_UPDATE,
                new MyLocationListener()
        );

        // 接近偵測設置
        addProximityAlert(DEFAULT_TTS);

        // 取得 Google Map 實體
        setUpMapIfNeeded();

        // 設定地圖類型
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 繪製地界
        drawCircle();

        // 移動到指定坐標
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_TTS, 17));

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    // 設置/加入接近偵測警示
    private void addProximityAlert(LatLng latlng) {

        // 建立廣播 PendingIntent
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // 加入接近偵測警示
        locationManager.addProximityAlert(
                latlng.latitude, // 地界中心點緯度坐標
                latlng.longitude, // 地界中心點經度坐標
                POINT_RADIUS, // 地界半徑(公尺)
                PROX_ALERT_EXPIRATION, // 偵測有效期限, -1表示永久有效
                proximityIntent // 偵測到移入或移出時所應觸發的intent
        );

        // 註冊接近偵測接收器
        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        receiver = new ProximityIntentReceiver();
        registerReceiver(receiver, filter);

    }

    // Location 監聽器
    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            // 清除地圖上所有物件
            mMap.clear();
            // 繪製標記
            LatLng latlng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            drawMarkers(latlng);
            // 重繪地界
            drawCircle();
            // 計算距離
            float distance = getDistanceBetween(latlng);
            setTitle("與地界中心距離：" + nf.format(distance) + " M");
        }
        public void onStatusChanged(String s, int i, Bundle b) {
        }
        public void onProviderDisabled(String s) {
        }
        public void onProviderEnabled(String s) {
        }
    }

    // 與地界中心的距離(公尺)
    private float getDistanceBetween(LatLng latlng) {
        float[] results = new float[1];
        Location.distanceBetween(DEFAULT_TTS.latitude, DEFAULT_TTS.longitude,
                latlng.latitude, latlng.longitude, results);
        return results[0];
    }

    // 繪製地界
    private void drawCircle() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    // 繪製標記
    private void drawMarkers(LatLng latlng) {

        // 建立 Marker
        MarkerOptions mo = new MarkerOptions();
        mo.position(latlng);
        mo.title("新地點");
        mo.snippet("緯經度:" + latlng);
        mo.anchor(0.5f, 1.0f);
        mo.draggable(true);

        // 將Marker加入到地圖中
        mMap.addMarker(mo);

    }

    @Override
    protected void onDestroy() {
        // 卸載接近偵測接收器
        if(receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
