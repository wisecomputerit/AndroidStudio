package com.ch19;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Date;

public class MainActivity extends Activity {

    private Context context;
    private TextView textView;
    private EditText editText;

    private static final String MESSAGE_PATH = "/wear/message";
    private static final String DATA_PATH = "/wear/send-data";
    private static final String DATA_KEY = "my_key";
    private static final String DATA_TIME = "my_time";

    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

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
            textView.setText("Google API Client 進行連線...\n" +
                    textView.getText().toString());
        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            Wearable.MessageApi.removeListener(mGoogleApiClient,
                    wearableMsgListener);
            mGoogleApiClient.disconnect();
            textView.setText("Google API Client 連線斷開");
        }
        super.onStop();
    }

    private MessageApi.MessageListener wearableMsgListener = new
            MessageApi.MessageListener() {
                @Override
                public void onMessageReceived(final MessageEvent messageEvent) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = null;
                            try {
                                // 取得Message中所附帶的資訊
                                s = new String(messageEvent.getData(), "UTF-8");
                                s = "Wear回傳：" + s;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            };

    private GoogleApiClient.ConnectionCallbacks googleApiClientConnCallback =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Wearable.MessageApi.addListener(mGoogleApiClient,
                            wearableMsgListener);
                    textView.setText("Google API Client連線成功" +
                            textView.getText().toString());
                }
                @Override
                public void onConnectionSuspended(int i) {
                    textView.setText("Google API Client 連線暫停");
                }
            };

    private GoogleApiClient.OnConnectionFailedListener googleApiClientOnConnFail =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult result) {
                    if (mResolvingError) {
                        textView.setText("已經嘗試解決一個錯誤");
                        return;
                    } else if (result.hasResolution()) {
                        try {
                            mResolvingError = true;
                            // 嘗試解決無法連線的錯誤
                            result.startResolutionForResult(MainActivity.this, 1001);
                        } catch (IntentSender.SendIntentException e) {
                            // 嘗試重新連線
                            mGoogleApiClient.connect();
                        }
                    } else {
                        textView.setText("錯誤代碼:" + result.getErrorCode());
                        mResolvingError = true;
                    }
                }
            };

    private class AsyncTaskSendMessage extends AsyncTask {
        @Override
        protected Void doInBackground(Object[] objects) {
            // 取得所有連線裝置。
            // await must not be called on the UI thread
            NodeApi.GetConnectedNodesResult connectedWearableDevices =
                    Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            // 取得連線裝置節點
            for (Node node : connectedWearableDevices.getNodes()) {
                // 將要傳送的文字轉成 byte[]
                byte[] payload = editText.getText().toString().getBytes();
                // 傳送訊息
                PendingResult<MessageApi.SendMessageResult> result = Wearable.MessageApi.sendMessage(
                        mGoogleApiClient, node.getId(), MESSAGE_PATH,
                        payload);
                // 設定與實作結果狀態的回呼
                result.setResultCallback(new ResultCallback() {

                    @Override
                    public void onResult(Result result) {
                        if (result.getStatus().isSuccess()) {
                            textView.setText("文字傳送成功！" + result.getStatus());

                        } else {
                            textView.setText("文字傳送失敗！" + result.getStatus());
                        }
                    }
                });
            }
            return null;
        }
    }

    // 傳送文字型資料
    private void sendText() {
        new AsyncTaskSendMessage().execute();
    }

    // 傳送物件型（二進制）資料
    private void sendData(final int rawId) {
        // 建立Uri物件，該物件是用來定義數據資源的所在位置。
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
        // 將數據資源透過建立Asset物件進行序列化
        Asset asset = Asset.createFromUri(uri);

        // 建立PutDataMapRequest集合（存放Asset序列化物件的集合容器）
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create(DATA_PATH);
        // 加入asset序列化物件資料
        dataMapRequest.getDataMap().putAsset(DATA_KEY, asset);
        // 加入資料時間。
        dataMapRequest.getDataMap().putLong(DATA_TIME, new Date().getTime());

        // 調用asPutDataRequest()取得PutDataRequest。
        // PutDataRequest是用於在Android Wear網路上建立新的資料請求
        PutDataRequest request = dataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        textView.setText("影像傳送成功！");
                        Drawable myIcon = resizeImage(rawId);
                        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, myIcon, null);
                    }
                });
    }

    // 調整圖形尺寸
    private Drawable resizeImage(int rawId) {
        Drawable image = getResources().getDrawable(rawId);
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 120, 120, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    public void onClick(View view) {
        int rawId = 0;
        switch (view.getId()) {
            case R.id.sendBtn:
                sendText();
                return;
            case R.id.button1:
                rawId = R.raw.icon1;
                break;
            case R.id.button2:
                rawId = R.raw.icon2;
                break;
            case R.id.button3:
                rawId = R.raw.icon3;
                break;
            case R.id.button4:
                rawId = R.raw.icon4;
                break;
        }
        sendData(rawId);
    }

}