package com.pcschool.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        context = this;

        // 設定地圖類型
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // 地圖上顯示建築物
        // 注意：zoom的設定要 >=17才會顯示建築物
        mMap.setBuildingsEnabled(true);
        // 顯示目前所在位置
        mMap.setMyLocationEnabled(true);

        // Google地圖使用者操作界面功能設定
        UiSettings ui = mMap.getUiSettings();
        // 開啟/關閉縮放鈕
        ui.setZoomControlsEnabled(true);
        // 開啟/關閉地圖捲動手勢
        ui.setScrollGesturesEnabled(true);
        // 開啟/關閉地圖縮放手勢
        ui.setZoomGesturesEnabled(true);
        // 開啟/關閉地圖傾斜手勢
        ui.setTiltGesturesEnabled(true);
        // 開啟/關閉地圖旋轉手勢
        ui.setRotateGesturesEnabled(true);

        // 按一下地圖即可取得該點經緯度
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // 根據point取得該經緯度所對應的地址/地標
                String address = Helper.getAddressByLatLng(point);
                if (address == null) {
                    Toast.makeText(context, "Not found !", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(context, address, Toast.LENGTH_SHORT).show();
                    // 演示地圖相機動畫效果
                    playAnimateCamera(point, 3000);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem info1 = menu.add(0, 1, 0, "台北車站");
        info1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                // 台北車站緯經度 : (25.047924,121.517081)
                LatLng latlng = new LatLng(25.047924, 121.517081);
                // 演示地圖相機動畫效果
                playAnimateCamera(latlng, 10000);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 在地圖上演示地圖相機動畫效果
    private void playAnimateCamera(LatLng latlng, int durationMs) {
        // 設置相關地圖相機位置參數，其中zoom的設定要 >=17才會顯示建築物
        CameraPosition cameraPos = new CameraPosition.Builder().target(latlng)
                .zoom(17.0f).bearing(300).tilt(45).build();
        // 定義地圖相機移動
        CameraUpdate cameraUpt = CameraUpdateFactory
                .newCameraPosition(cameraPos);
        // 地圖相機動畫行程設定
        mMap.animateCamera(cameraUpt, durationMs, null);
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
