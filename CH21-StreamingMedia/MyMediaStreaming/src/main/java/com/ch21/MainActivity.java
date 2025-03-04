package com.ch21;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends Activity {

    private ImageButton buttonPlayPause;
    public EditText editTextSongURL;
    private SeekBar seekBarProgress;

    private MediaPlayer mediaPlayer;

    private SurfaceView mPreview;
    private SurfaceHolder holder;

    // 媒體是否已經準備好(對應媒體準備好監聽器) ?
    private boolean isOnPrepared = false;
    // 媒體是否已經播放完畢(對應媒體播放完畢監聽器) ?
    private boolean isOnCompled  = true;

    private Context context;

    // 媒體資料總長度(微秒)
    private int mediaFileLengthInMilliseconds;
    private final Handler handler = new Handler();

    private WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        buttonPlayPause = (ImageButton)findViewById(R.id.ButtonTestPlayPause);

        editTextSongURL = (EditText)findViewById(R.id.EditTextSongURL);
        editTextSongURL.setText(R.string.rtsp_mov);

        seekBarProgress = (SeekBar)findViewById(R.id.SeekBarTestPlay);
        seekBarProgress.setMax(99); // It means 100% .0-99
        seekBarProgress.setOnTouchListener(new MyOnTouchListener());

        // 取得並管理 Wifi
        WifiManager wifiManager = ((WifiManager) getSystemService(Context.WIFI_SERVICE));
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "MyWifiLock");

        // 建立 mediaPlayer 物件
        mediaPlayer = new MediaPlayer();
        // 註冊:媒體準備好監聽器
        mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
        // 註冊:媒體播放完畢監聽器
        mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());
        // 註冊:媒體緩衝監聽器
        mediaPlayer.setOnBufferingUpdateListener(new MyOnBufferingUpdateListener());
        // 媒體串流設定
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 建立快速畫布
        mPreview = (SurfaceView) findViewById(R.id.surface);
        // 取得 Surface Holder 物件, 用來處理繪圖效果與動畫
        holder = mPreview.getHolder();

    }

    // 透過 PrimaryProgress 來記錄播放進度
    private void primarySeekBarProgressUpdater() {
        if(mediaPlayer == null) {
            return;
        }
        // 計算目前播放進度 (%)
        float progessPercentage = (float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds;
        // 將目前播放進度 (%) * 100, 例如:
        // 若 progessPercentage = 0.6
        // 則 progess = 0.6 * 100 = 60
        int progess = (int)(progessPercentage * 100);

        seekBarProgress.setProgress(progess);

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    // 遞迴呼叫
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    // 按下播放/暫停 Button
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ButtonTestPlayPause :
                if(!isOnPrepared) { // 判斷 mediaPlayer 是否已經準備好 ?
                    // 透過 AsyncTask 來取得媒體串流
                    new AsyncTaskLoadVideoProgress().execute();
                } else if(isOnPrepared) {
                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                    if(mediaPlayer.isPlaying()){ // 判斷 mediaPlayer 是否正在播放 ?
                        mediaPlayer.pause();
                        buttonPlayPause.setImageResource(R.drawable.button_play);
                    } else {
                        mediaPlayer.start();
                        buttonPlayPause.setImageResource(R.drawable.button_pause);
                    }
                    primarySeekBarProgressUpdater();
                }
                break;
        }
    }

    // 非同步工作排程
    private class AsyncTaskLoadVideoProgress extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "媒體準備中", "請稍待 !");
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            try {
                mediaPlayer.setDataSource(editTextSongURL.getText().toString());
                mediaPlayer.setDisplay(holder);
                mediaPlayer.prepare();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Boolean flag) {
            if(flag) {
                progressDialog.dismiss();
            } else {
                Toast.makeText(context, "資料載入失敗 !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(wifiLock != null) {
            wifiLock.acquire();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            // 媒體物件釋放
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(wifiLock != null) {
            wifiLock.release();
        }
    }

    // 媒體準備好監聽器
    private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) { // 媒體準備好之後的回呼函式

            isOnPrepared = true; // 已準備好
            isOnCompled = false; // 尚未播放完畢

            // 取得媒體總長度
            mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
            // 開始播放
            mediaPlayer.start();
            primarySeekBarProgressUpdater();
            buttonPlayPause.setImageResource(R.drawable.button_pause);
            Toast.makeText(context, "開始播放!", Toast.LENGTH_SHORT).show();

        }
    }

    // 媒體播放完畢監聽器
    private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) { // 媒體播放執行完畢後的回呼函式
            isOnCompled = true; // 已播放完畢
            buttonPlayPause.setImageResource(R.drawable.button_play);
            Toast.makeText(context, "影片結束!", Toast.LENGTH_SHORT).show();
        }
    }

    // 媒體緩衝監聽器
    private class MyOnBufferingUpdateListener implements MediaPlayer.OnBufferingUpdateListener {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            // 透過SecondaryProgress來記錄載入進度
            seekBarProgress.setSecondaryProgress(percent);
        }
    }

    // Touch監聽器
    private class MyOnTouchListener implements View.OnTouchListener {
        // for OnTouchListener 介面
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch(view.getId()) {
                case R.id.SeekBarTestPlay :
                    if(mediaPlayer.isPlaying()){
                        SeekBar sb = (SeekBar)view;
                        int playPositionInMillisecconds =
                                (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                        mediaPlayer.seekTo(playPositionInMillisecconds);
                    }
                    break;
            }
            return false;
        }
    }
}
