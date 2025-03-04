package com.pcschool.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    // 桃園火車站 (24.989206, 121.313549)
    private final LatLng DEFAULT_TTS = new LatLng(24.99028, 121.31576);
    private GoogleMap mMap;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        setUpMapIfNeeded();

        // 設定地圖
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // 繪製標記
        drawMarker();
        // 按下Marker事件
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(context, "這裡是:" + marker.getTitle(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // 移動Marker事件
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setTitle("新地點");
                marker.setSnippet("緯經度:" + marker.getPosition());
                // 地圖畫面中心也一同改變到新的位置
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        marker.getPosition(), 15));
            }
            @Override
            public void onMarkerDragStart(Marker marker) {
            }
        });
        // 按下Info-Window事件
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(context, "這裡是:" + marker.getSnippet(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void drawMarker() {
        // 1.預設LatLng
        LatLng latlng = DEFAULT_TTS;
        // 2.建立 Marker
        MarkerOptions options = new MarkerOptions(); // 建立標記選項的實例
        options.position(latlng); // 標記經緯度
        options.title("桃園火車站"); // Info-Window標題
        options.snippet("緯經度:" + latlng); // Info-Window標記摘要
        options.anchor(0.5f, 1.0f); // 錨點
        options.draggable(true); // 是否可以拖曳標記?
        // 3.將Marker加入到地圖中
        mMap.addMarker(options);
        // 4.移動到Marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
