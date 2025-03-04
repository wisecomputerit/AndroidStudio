package com.ch19;

import android.app.Activity;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends Activity {

    private static final String MESSAGE_PATH = "/wear/message";
    private static final String DATA_PATH = "/wear/send-data";
    private static final String DATA_KEY = "my_key";

    private GoogleApiClient mGoogleApiClient;

    private boolean mResolvingError = false;

    private TextView mTextView;
    private View mViewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mViewRoot = (View) stub.findViewById(R.id.viewRoot);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(googleApiClientConnCallback)
                .addOnConnectionFailedListener(googleApiClientOnConnFail)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            Wearable.MessageApi.removeListener(mGoogleApiClient, wearableMsgListener);
            Wearable.DataApi.removeListener(mGoogleApiClient, wearableDataListener);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private GoogleApiClient.ConnectionCallbacks googleApiClientConnCallback =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    mResolvingError = false;
                    Wearable.MessageApi.addListener(mGoogleApiClient,
                            wearableMsgListener);
                    Wearable.DataApi.addListener(mGoogleApiClient,
                            wearableDataListener);
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Toast.makeText(getApplicationContext(),
                            "Google API Client無法連線。", Toast.LENGTH_LONG)
                            .show();
                }
            };

    private GoogleApiClient.OnConnectionFailedListener googleApiClientOnConnFail =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    if (mResolvingError) {
                        return;  // 已經嘗試解決一個錯誤
                    } else if (connectionResult.hasResolution()) {
                        try {    // 嘗試解決無法連線的錯誤
                            mResolvingError = true;
                            connectionResult.startResolutionForResult(MainActivity.this, 1001);
                        } catch (IntentSender.SendIntentException e) {
                            // 嘗試重新連線
                            mResolvingError = false;
                            mGoogleApiClient.connect();
                        }
                    } else {
                        mResolvingError = false;
                        Wearable.MessageApi.removeListener(mGoogleApiClient, wearableMsgListener);
                        Wearable.DataApi.removeListener(mGoogleApiClient, wearableDataListener);
                    }
                }
            };

    private MessageApi.MessageListener wearableMsgListener = new
            MessageApi.MessageListener() {
                @Override
                public void onMessageReceived(final MessageEvent messageEvent) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = null;
                            try {
                                // 比對路徑
                                if(messageEvent.getPath().equals(MESSAGE_PATH)) {
                                    // 取得message中的資料
                                    s = new String(messageEvent.getData(), "UTF-8");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
                                    .show();

                            // 將訊息回傳給Phone
                            String noteId = messageEvent.getSourceNodeId();
                            new AsyncTaskReplyMessage().execute(noteId);
                        }
                    });
                }
            };

    private class AsyncTaskReplyMessage extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            byte[] payload = "收到了".getBytes();
            // 訊息回傳
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                    mGoogleApiClient, strings[0], MESSAGE_PATH,
                    payload).await();
            return null;
        }
    }

    private DataApi.DataListener wearableDataListener =
            new DataApi.DataListener() {
        @Override
        public void onDataChanged(DataEventBuffer dataEvents) {
            // 取得目前收到的所有資料項目。
            List<DataEvent> listDataEvents =
                    FreezableUtils.freezeIterable(dataEvents);
            dataEvents.close();

            // 比對每一筆資料項目，找出我們需要的資料。
            for (DataEvent event : listDataEvents) {
                String path = event.getDataItem().getUri().getPath();

                if (path.equals(DATA_PATH)) {
                    DataMapItem dataMapItem =
                            DataMapItem.fromDataItem(event.getDataItem());
                    // 取得asset序列化物件
                    Asset asset = dataMapItem.getDataMap()
                            .getAsset(DATA_KEY);
                    // 取得數據資料的串流物件。
                    InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                            mGoogleApiClient, asset).await().getInputStream();
                    // 將數據資料轉成bitmap
                    final Bitmap bitmap =
                            BitmapFactory.decodeStream(assetInputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Drawable da = new BitmapDrawable(getResources(), bitmap);
                            mViewRoot.setBackground(da);
                        }
                    });
                }
            }
        }
    };

}
