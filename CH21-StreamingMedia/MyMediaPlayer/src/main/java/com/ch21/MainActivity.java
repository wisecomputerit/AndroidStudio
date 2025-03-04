package com.ch21;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private Context context;
    private SeekBar runControl, volControl;
    private boolean run = false; // 用來控制progress是否要進行 !
    private int mMax; // MediaPlayer 音樂總時間
    private int sMax; // SeekBar 最大值
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(context, R.raw.nothing_on_you);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // 控制播放進度
        runControl = (SeekBar)findViewById(R.id.SeekBar01);
        runControl.setOnSeekBarChangeListener(new MyRunControlOnSeekBarChangeListener());

        // 控制聲音大小
        volControl = (SeekBar)findViewById(R.id.SeekBar02);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new MyVolControlOnSeekBarChangeListener());

    }

    @Override
    protected void onStop() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
        }
        run = false;
        super.onStop();
    }

    //----------------------------------------------------------------
    // 因為MediaPlay沒有播放進度回呼函示, 所以必須要自建一條執行緒去執行變更
    //----------------------------------------------------------------
    // 1.建立startProgressUpdate方法
    public void startProgressUpdate() {
        run = true;
        mMax = mediaPlayer.getDuration(); // 播放總時間
        sMax = runControl.getMax(); // SeekBar 最大值
        new DelayThread().start();
    }

    // 2.實作Handle用來變更SeekBar UI
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(run) {
                // 取得 Message 物件內的資料
                int progess = msg.getData().getInt("progessValue");
                setTitle("進度:" + progess + "/100");
                runControl.setProgress(progess);
            }
        }
    };

    // 3.實作一個Delay執行緒
    public class DelayThread extends Thread {
        public void run() {
            while(run){
                try {
                    sleep(100);
                } catch (InterruptedException e) {}

                // 1.建立Message物件
                Message msg = new Message();

                // 2.準備要傳遞給UI執行緒的資料(Bundle)
                Bundle data = new Bundle();
                int position = mediaPlayer.getCurrentPosition();
                // 當下播放進度轉 progress
                int progess = position * sMax / mMax;

                // 3.將資料放入Bundle並掛載到Message物件中
                data.putInt("progessValue", progess);
                msg.setData(data);

                // 4.傳遞給UI執行緒
                // 子執行緒透過sendMessage(msg)將資料傳遞給 UI 執行緒
                // 回呼 public void handleMessage(Message msg)
                mHandle.sendMessage(msg);
            }
        }
    }

    //----------------------------------------------------------------

    // 調整播放進度
    private class MyRunControlOnSeekBarChangeListener
            implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int dest = seekBar.getProgress();  // 取得目前播放進度

            // 設定播放位置
            int progess = mMax * dest / sMax;
            mediaPlayer.seekTo(progess);
        }
    }

    // 調整聲音大小
    private class MyVolControlOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.Button01:
                Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
                try {
                    if(mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.prepare();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            mediaPlayer.start();
                            // 同步到播放進度函式
                            run = true;
                            startProgressUpdate();
                        }
                    });

                } catch(Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
                setTitle("Start...");
                break;
            case R.id.Button02:
                Toast.makeText(context, "Pause", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                setTitle("Pause...");
                break;
            case R.id.Button03:
                Toast.makeText(context, "Stop", Toast.LENGTH_SHORT).show();
                mediaPlayer.stop();
                run = false;
                setTitle("Stop...");
                break;
        }

    }
}
