package com.ch26.sinvoice;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.libra.sinvoice.LogHelper;
import com.libra.sinvoice.SinVoiceRecognition;

public class ReceiverActivity extends ActionBarActivity
        implements SinVoiceRecognition.Listener {

    private final static String TAG = "ReceiverActivity";

    // 設定顯示辨識內容常數
    private final static int MSG_SET_RECG_TEXT = 1;
    // 設定辨識進行常數
    private final static int MSG_RECG_START = 2;
    // 設定結束辨識常數
    private final static int MSG_RECG_END = 3;

    private final static String CODEBOOK = "12345";

    private Handler mHanlder;
    private SinVoiceRecognition mRecognition;

    private static ImageView imageView1;
    private static HashMap<String, Integer> map;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        map = new HashMap<String, Integer>();
        map.put("123", R.drawable.line1);
        map.put("232", R.drawable.line2);
        map.put("323", R.drawable.line3);
        map.put("132", R.drawable.line4);

        setTitle("聲音控制-接收端");

        mRecognition = new SinVoiceRecognition(CODEBOOK);
        mRecognition.setListener(this);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
        mHanlder = new RegHandler(recognisedTextView);

        ToggleButton toggleButton1 = (ToggleButton) findViewById(R.id.toggleButton1);

        toggleButton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context, isChecked + "", Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    mRecognition.start();
                } else {
                    mRecognition.stop();
                }
            }

        });

    }

    private static class RegHandler extends Handler {
        private TextView mRecognisedTextView;
        private static StringBuilder mTextBuilder = new StringBuilder();
        public RegHandler(TextView textView) {
            mRecognisedTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 顯示辨識內容
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    mTextBuilder.append(ch);
                    if (null != mRecognisedTextView) {
                        mRecognisedTextView.setText(mTextBuilder.toString());
                    }
                    break;
                // 清除辨識字串內容
                case MSG_RECG_START:
                    mTextBuilder.delete(0, mTextBuilder.length());
                    break;
                // 結束辨識
                case MSG_RECG_END:
                    // 得到最新編碼key。
                    String key = mTextBuilder.toString();
                    // 將key對應到map中。
                    final Integer value = map.get(key);
                    if (value != null) {
                        imageView1.setImageResource(value);
                    }
                    LogHelper.d(TAG, "recognition end");
                    break;
            }
            super.handleMessage(msg);
        }
    }

    // 辨識開始
    @Override
    public void onRecognitionStart() {
        mHanlder.sendEmptyMessage(MSG_RECG_START);
    }

    // 辨識進行中
    @Override
    public void onRecognition(char ch) {
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    // 辨識完成
    @Override
    public void onRecognitionEnd() {

        mHanlder.sendEmptyMessage(MSG_RECG_END);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent();
        i.setClass(this, SendActivity.class);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("傳送端");
        return super.onCreateOptionsMenu(menu);
    }
}
