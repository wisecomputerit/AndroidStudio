package com.ch25;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private ImageView imageView;
    private TextView textView;
    private int[] gopher;
    private boolean play;
    private Handler handler;
    private GopherSprite g1;
    private SoundPool soundPool;
    private int touchId;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        // 建立地鼠輪詢陣列
        gopher = new int[] {R.drawable.hole,
                            R.drawable.mole1, R.drawable.mole2, R.drawable.mole3,
                            R.drawable.mole2, R.drawable.mole1, R.drawable.hole};
        handler = new Handler();

        // 建立地鼠遊戲物件
        g1 = new GopherSprite(imageView);
        // 建立音效池
        buildSoundPool();
        // 註冊 onTouch 監聽器
        imageView.setOnTouchListener(new GopherOnTouchListener());
    }

    private class GopherSprite implements Runnable {
        ImageView imageView;
        int idx;
        boolean hit;
        GopherSprite(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        public void run() {
            draw();
        }
        private void draw() {
            if(!play) {
                return;
            }
            if(hit) {
                imageView.setImageResource(R.drawable.mole4);
                hit = false;
                idx = 0;
                handler.postDelayed(this, 1000);
            } else {
                idx = idx % gopher.length;
                imageView.setImageResource(gopher[idx]);
                handler.postDelayed(this, 300);
                idx = ++idx % gopher.length;
            }
        }
    }

    private class GopherOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(play && event.getAction() == MotionEvent.ACTION_DOWN) {
                if(gopher[g1.idx] == R.drawable.mole2 ||
                        gopher[g1.idx] == R.drawable.mole3) {
                    g1.hit = true;
                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
                    textView.setText(String.valueOf(++score));
                } else {
                    textView.setText(String.valueOf(--score));
                }
            }
            return false;
        }
    }

    // 建立音效池
    private void buildSoundPool() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attr)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        touchId = soundPool.load(this, R.raw.touch, 1);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            play = true;
            score = 0;
            textView.setText("0");
            item.setEnabled(false);
            new CountDownTimer(10000, 1000){
                @Override
                public void onFinish() {
                    play = false;
                    item.setEnabled(true);
                    setTitle("剩餘時間：0");
                }
                @Override
                public void onTick(long millisUntilFinished) {
                    setTitle("剩餘時間：" + millisUntilFinished/1000);
                }

            }.start();
            handler.post(g1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
