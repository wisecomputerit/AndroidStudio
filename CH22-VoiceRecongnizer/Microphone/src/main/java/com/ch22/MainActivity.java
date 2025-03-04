package com.ch22;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textView1, textView2;
    private RecordThread rt;
    private Handler handler = new Handler();
    private Context context;
    private int count; // 吹氣成功次數

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);

        rt = new RecordThread();
        rt.start();
    }

    class RecordThread extends Thread {

        private AudioRecord audioRecord;
        private int minBufferSize;
        private static final int SAMPLE_RATE_IN_HZ = 8000; // 8000, 16000, 32000, 44100
        private boolean isRun = false;

        private int blowBufferDelta = 2500; // 調整值 > 2500 列入採樣計算
        private int blowCountDelta = 15; // 採樣連續累積次數 > n 代表有吹氣動作
        private int tmpCountDelta; // 累計連續可能吹氣

        public RecordThread() {
            super();
            minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE_IN_HZ,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    minBufferSize);
        }

        public void run() {
            super.run();

            audioRecord.startRecording();
            byte[] buffer = new byte[minBufferSize];
            isRun = true;
            while (isRun) {
                // 將錄音資料讀到 buffer[]
                audioRecord.read(buffer, 0, minBufferSize);
                int o_buffer = 0; // 記錄原始資訊
                int m_buffer = 0; // 記錄調整後資訊( buffer * buffer / 長度 --> 自定單位量)

                for (int i = 0, lens = buffer.length ; i < lens ; i++) {
                    o_buffer += buffer[i]; // 原始資訊
                    m_buffer += (Math.pow(buffer[i], 2)) / buffer.length; // 原始資訊( buffer * buffer / 長度 --> 自定單位量)
                }

                final double value1 = o_buffer;
                final double value2 = m_buffer;

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        textView1.setText("緩衝大小:" + minBufferSize +
                                "\n原始值:" + value1 +
                                "\n修正後:" + String.valueOf(value2) +
                                "\n\n吹氣次數");

                        if(value2 > blowBufferDelta) { // 調整值 > 2500 列入採樣計算
                            tmpCountDelta++;
                        } else {
                            tmpCountDelta = 0;
                        }
                        if(tmpCountDelta > blowCountDelta) { // 採樣連續累積次數 > n 代表有吹氣動作
                            count++;
                            tmpCountDelta = 0;

                        }
                        textView2.setText(String.valueOf(count));
                    }
                });


            }
            audioRecord.stop();
            audioRecord.release();
        }

        public void pause() {
            isRun = false;
        }

        public void start() {
            if (!isRun) {
                super.start();
            }
        }
    }

    public void onClick(View view) {
        count = 0;
        textView2.setText(String.valueOf(count));
    }
    @Override
    public void onPause() {
        super.onPause();
        rt.pause();

    }
}
