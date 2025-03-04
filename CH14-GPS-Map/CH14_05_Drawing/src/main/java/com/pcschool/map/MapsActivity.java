package com.pcschool.map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MapsActivity extends FragmentActivity {

    // 桃園火車站 (24.989206, 121.313549)
    private final LatLng DEFAULT_TTS = new LatLng(24.99028, 121.31576);
    private GoogleMap mMap;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // 設定地圖
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // 畫單一線段
        drawPolyLine();
        // 畫連續線段
        drawPolyLineAll();
        // 畫連續線段-KML
        drawPolyLineKML();
        // 畫多邊形
        drawPolygon();
        // 畫圓形
        drawCircle();
        // 桃園火車站位置
        LatLng tts = DEFAULT_TTS;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tts, 17));

    }

    // 畫單一線段
    private void drawPolyLine() {
        LatLng p1 = new LatLng(24.990194, 121.311767);
        LatLng p2 = new LatLng(24.989206, 121.313549);

        PolylineOptions options = new PolylineOptions();
        options.add(p1, p2);
        options.width(5);
        options.color(Color.MAGENTA);
        options.zIndex(1); // 疊層id(數字越高圖層越上層)

        mMap.addPolyline(options);
    }

    // 畫連續線段
    private void drawPolyLineAll() {
        List<LatLng> points = Arrays.asList(
                new LatLng(24.99159, 121.31449),
                new LatLng(24.99108, 121.31497),
                new LatLng(24.99059, 121.31548),
                new LatLng(24.99046, 121.31558),
                new LatLng(24.99028, 121.31576),
                new LatLng(24.99024, 121.3158),
                new LatLng(24.99018, 121.31588),
                new LatLng(24.99014, 121.31595),
                new LatLng(24.99008, 121.31606));

        PolylineOptions options = new PolylineOptions();
        options.addAll(points);
        options.width(5);
        options.color(Color.CYAN);
        options.zIndex(1); // 疊層id(數字越高圖層越上層)

        mMap.addPolyline(options);
    }

    // 畫連續線段-KML
    private void drawPolyLineKML() {
        List<LatLng> points = new ArrayList<LatLng>();
        try {
            // 取得 res\router.kml 資源
            InputStream inStream = getResources().openRawResource(R.raw.router);
            // 建立 DOM 實例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 取得 DOM 文件
            Document doc = db.parse(inStream);
            // 找到 "coordinates" XML 節點元素
            NodeList nl = doc.getElementsByTagName("coordinates");
            if(nl.getLength() == 0) {
                return;
            }
            // 取得節點
            Node node = nl.item(0);
            // 分析節點元素內容
            String[] routers = node.getTextContent().trim().split(" ");
            for(String router : routers) {
                String[] r = router.split(",");
                // 將經緯度物件加入到 points 集合中
                points.add(new LatLng(Double.parseDouble(r[1]),
                        Double.parseDouble(r[0])));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return;
        }

        PolylineOptions options = new PolylineOptions();
        options.addAll(points);
        options.width(5);
        options.color(Color.CYAN);
        options.zIndex(1); // 疊層id(數字越高圖層越上層)

        mMap.addPolyline(options);
    }

    // 畫多邊形
    private void drawPolygon() {
        LatLng p1 = new LatLng(24.990194, 121.311767);
        LatLng p2 = new LatLng(24.989513, 121.312015);
        LatLng p3 = new LatLng(24.989916, 121.313306);
        LatLng p4 = new LatLng(24.990571, 121.313075);

        PolygonOptions options = new PolygonOptions();
        options.add(p1, p2, p3, p4);
        options.strokeWidth(5);
        options.strokeColor(Color.BLUE);
        options.fillColor(Color.argb(200, 100, 150, 0));
        options.zIndex(2); // 疊層id(數字越高圖層越上層)

        mMap.addPolygon(options);
    }

    // 畫圓形
    private void drawCircle() {
        LatLng latlng = new LatLng(24.989206, 121.313549);
        // 以 latlng 位置為中心畫圓
        CircleOptions options = new CircleOptions();
        options.center(latlng); // 圓心位置
        options.radius(100); // 半徑(公尺)
        options.strokeWidth(5); // 圓形外框寬度
        options.strokeColor(Color.TRANSPARENT); // 圓形外框顏色
        options.fillColor(Color.argb(150, 255, 0, 0));
        options.zIndex(3); // 疊層id(數字越高圖層越上層)

        mMap.addCircle(options);
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
