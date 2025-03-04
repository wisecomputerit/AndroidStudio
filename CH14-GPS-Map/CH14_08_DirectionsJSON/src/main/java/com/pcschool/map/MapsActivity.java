package com.pcschool.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pcschool.utils.Directions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    public static final LatLng STATION = new LatLng(24.989206, 121.313549);
    private GoogleMap mMap;
    private ArrayList<LatLng> markerPoints;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;

        // 儲存使用者點選的任意二點
        markerPoints = new ArrayList<LatLng>();

        setUpMapIfNeeded();


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(STATION, 16));

        if (mMap != null) {
            setTitle("地圖載入中...");
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {
                    setTitle(R.string.app_name);
                    Toast.makeText(context, "地圖載入完成!請任意點二點進行導航.",
                            Toast.LENGTH_SHORT).show();
                }

            });

            // 地圖onClick監聽器
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // 確認地圖上的標記是否 >= 2
                    // 若二點(含)以上就清除地圖
                    if (markerPoints.size() >= 2) {
                        markerPoints.clear();
                        mMap.clear();
                    }

                    // 增加新標記
                    markerPoints.add(point);

                    // 建立標記選項
                    MarkerOptions options = new MarkerOptions();

                    // 設置標記選項位置
                    options.position(point);

                    // 起始及終點位置符號顏色
                    if (markerPoints.size() == 1) {
                        // 起點符號顏色
                        options.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else if (markerPoints.size() == 2) {
                        // 終點符號顏色
                        options.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }

                    mMap.addMarker(options);

                    // 判斷地圖上的marker數量是否>=2
                    if (markerPoints.size() >= 2) {
                        // 繪製導航路線
                        Directions.getInstance().draw(context, markerPoints.get(0),
                                markerPoints.get(1), mMap, Directions.MODE_DRIVING);
                    }

                }
            });
        }
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
