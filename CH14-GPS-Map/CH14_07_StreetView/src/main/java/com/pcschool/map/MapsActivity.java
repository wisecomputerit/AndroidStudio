package com.pcschool.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private final String ADDRESS = "台北市忠孝東路四段169號";
    private final LatLng DEFAULT_TTS = new LatLng(25.0415471,121.5512709);
    private GoogleMap mMap;
    private Context context;
    private GroundOverlay groundOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        setUpMapIfNeeded();

        // 設定地圖
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // 繪製標記
        drawMarkers();
        // 按下Marker事件
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(context, "這裡是:" + marker.getTitle(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // 按下Info-Window事件
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(context, "這裡是:" + marker.getSnippet(),
                        Toast.LENGTH_SHORT).show();
                LatLng latLng = marker.getPosition();
                // 取得街景
                String path = "google.streetview:cbll=%s,%s&mz=21";
                path = String.format(path, latLng.latitude, latLng.longitude);

                Uri uri = Uri.parse(path);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        uri);
                startActivity(intent);
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
                marker.setSnippet("按我一下可以看到動態街景");
                // 地圖畫面中心也一同改變
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        marker.getPosition(), 15));
                LatLng latlng = marker.getPosition();
                setStreetViewThumbnails(latlng);
            }
            @Override
            public void onMarkerDragStart(Marker marker) {
                if(groundOverlay != null) {
                    groundOverlay.remove(); // 移除街景縮略圖
                }
            }
        });

    }

    private void drawMarkers() {
        // 1.預設地點
        LatLng latlng = DEFAULT_TTS;
        // 2.建立 Marker
        MarkerOptions mo = new MarkerOptions();
        mo.position(latlng);
        mo.title(ADDRESS);
        mo.snippet("緯經度:" + latlng);
        mo.anchor(0.5f, 1.0f);
        mo.draggable(true);
        // 3.將Marker加入到地圖中
        mMap.addMarker(mo);
        // 4.移動到Marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        // 5.設置街景縮略圖
        setStreetViewThumbnails(latlng);
    }

    // 設置街景縮略圖
    private void setStreetViewThumbnails(final LatLng latlng) {
        // Google Street View Image API
        String url = "http://maps.googleapis.com/maps/api/streetview?"+
                "size=450x250&location=%s,%s&sensor=true";
        url = String.format(url, latlng.latitude, latlng.longitude);
        // 利用 Volley 取得街景縮略圖
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        groundOverlay = mMap.addGroundOverlay (
                                new GroundOverlayOptions()
                                        .image(BitmapDescriptorFactory.fromBitmap(response))
                                        .anchor(0, 1.5f)
                                        .position(latlng, 400f, 200f));
                        groundOverlay.setTransparency(0.3f); // 透明度
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mQueue.add(imageRequest);
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
